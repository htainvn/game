package com.example.game.services;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.EmptyGameStateEvent;
import com.example.game.config.GameConfig.GameEndStateEvent;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.datacontainer.interfaces.IChoiceDictionary;
import com.example.game.datacontainer.interfaces.IGameDataDictionary;
import com.example.game.datacontainer.interfaces.IPlayerDictionary;
import com.example.game.datacontainer.interfaces.IScoreDictionary;
import com.example.game.dto.OriginalQuizDto;
import com.example.game.entities.Game;
import com.example.game.executor.GameExecutor;
import com.example.game.factory.GameFactory;
import com.example.game.helper.RandomCode;
import com.example.game.model.GameSettingsModel;
import com.example.game.model.QuestionModel;
import com.google.gson.Gson;
import java.util.Date;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

// Facade
@Slf4j
@Service
public class GameService {
  private final HashMap<String, GameExecutor> games = new HashMap<>();
  private IPlayerDictionary players;
  private IChoiceDictionary choices;
  private IScoreDictionary scores;
  private IGameDataDictionary gameData;
  private final DataService dataService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final SimpUserRegistry simpUserRegistry;

  @Autowired
  public GameService(
      IPlayerDictionary players,
      IChoiceDictionary choices,
      IScoreDictionary scores,
      IGameDataDictionary gameData,
      DataService dataService,
      SimpMessagingTemplate simpMessagingTemplate,
      SimpMessagingTemplate simpMessagingTemplate1, SimpUserRegistry simpUserRegistry) {
    this.players = players;
    this.choices = choices;
    this.scores = scores;
    this.gameData = gameData;
    this.dataService = dataService;
    this.simpMessagingTemplate = simpMessagingTemplate1;
    this.simpUserRegistry = simpUserRegistry;
  }

  @Value("${quiz_service_url}")
  private String quizServiceUrl;

  public GameExecutor getGame(String gameid) {
    return games.get(gameid);
  }

  public Pair<String, String> /* <party_id, access_code> */ createGame(
      String quiz_id,
      String host_id,
      String gameFlowMode,
      String gradingStrategy,
      String authHeader
  ) {
    GameFactory gameFactory = new GameFactory(
        dataService,
        simpMessagingTemplate,
        simpUserRegistry
    );
    try {
      GameExecutor game = gameFactory.createGame(gameFlowMode, gradingStrategy);
      String[] authSplit = authHeader.split(" ");
      HashMap<String, String> headers = new HashMap<>();
      headers.put("Authorization", authSplit[1]);
      String quizData = new RestService()
          .getWithHeaders(
              quizServiceUrl + "quiz/" + quiz_id,
              "",
              headers
          );
      Gson gson = new Gson();
      OriginalQuizDto originalQuizDto = gson.fromJson(quizData, OriginalQuizDto.class);
      Game gameDataObj = new Game(game.getGameID(), originalQuizDto);
      HashMap<String, Object> params = new HashMap<>();
      params.put(ParamName.EVENT, EmptyGameStateEvent.BIND);
      params.put(ParamName.DATA, gameDataObj);
      HashMap<String, Object> result = game.execute(params);
      if (!result.get("status").equals("success")) {
        throw new Exception("Failed to create game");
      }
      String accessCode = RandomCode.generate(GameConfig.CODE_LENGTH);
      game.setAccessCode(accessCode);
      games.put(game.getGameID(), game);
      gameData.store(
          game.getGameID(),
          new GameSettingsModel(
              game.getGameID(),
              gameFlowMode,
              gradingStrategy,
              false,
              false
          )
      );
//      dataService.store("game", gameQuizDto);
      return Pair.with(game.getGameID(), game.getAccessCode());
    } catch (Exception e) {
      e.printStackTrace();
      log.atTrace().log("Failed to create game");
      return null;
    }
  }

  public void startGame(
      String party_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.LobbyStateEvent.START_GAME);
    params.put(ParamName.GAME_DATA_DICTIONARY, gameData);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
    QuestionModel questionModel = (QuestionModel) result.get(ParamName.QUESTION);
    assert (questionModel.getParty_id() != null);
  }

  public HashMap<String, Object> endGame(
      String party_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameEndStateEvent.GET_FINAL_RANK);
    params.put(ParamName.SCORE_DICTIONARY, scores);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
    return result;
  }

  public Pair<String, String>/* < status, player_id > */ registerPlayerToGame(
      String party_id,
      String player_name,
      String wsUserID
  ) {
//    try {
      GameExecutor game = games.get(party_id);
      HashMap<String, Object> params = new HashMap<>();
      params.put(ParamName.EVENT, GameConfig.LobbyStateEvent.REGISTER);
      params.put(ParamName.NAME, player_name);
      params.put(ParamName.PLAYER_DICTIONARY, players);
      params.put(ParamName.SCORE_DICTIONARY, scores);
      params.put(ParamName.GAME_DATA_DICTIONARY, gameData);
      params.put(ParamName.WS_USER_NAME, wsUserID);
      HashMap<String, Object> result = game.execute(params);
      return Pair.with(result.get(ParamName.STATUS_PR).toString(), result.get(ParamName.PLAYER_ID).toString());
//    }
//    catch (Exception e) {
//      throw new Exception("Failed to register player");
//      return Pair.with("failed", "");
//    }
//    if (!result.get("status").equals("success")) {
//      throw new UnsupportedOperationException();
//    }
  }

  public HashMap<String, Object> showQuestion(
      String party_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QShowingStateEvent.SHOW_QUESTION);
    params.put(ParamName.GAME_DATA_DICTIONARY, gameData);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
    HashMap<String, Object> response = new HashMap<>();
    response.put(ParamName.QUESTION, result.get(ParamName.QUESTION));
    response.put(ParamName.QUESTION_TIME_OUT, result.get(ParamName.QUESTION_TIME_OUT));
    response.put(ParamName.CURRENT_QUESTION_CNT, result.get(ParamName.CURRENT_QUESTION_CNT));
    return response;
  }

  public void timeOutShowingQuestion(String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QShowingStateEvent.TIME_OUT);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public void timeOutAnsweringQuestion(String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QAnsweringStateEvent.TIME_OUT);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public void kickPlayer(String party_id, String player_id) {
    throw new UnsupportedOperationException();
  }

  public void lockGame(String party_id) {
    throw new UnsupportedOperationException();
  }

//  public void answerQuestion(
//      String party_id) {
//    GameExecutor game = games.get(party_id);
//    HashMap<String, Object> params = new HashMap<>();
//    params.put(ParamName.EVENT, GameConfig.QShowingStateEvent.ANSWER_QUESTION);
//    HashMap<String, Object> result = game.execute(params);
//    if (!result.get("status").equals("success")) {
//      throw new UnsupportedOperationException();
//    }
//  }

  public void receiveAnswer(
      String party_id,
      String player_id,
      Long answer_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QAnsweringStateEvent.SEND_CHOICE);
    params.put(ParamName.PLAYER_ID, player_id);
    params.put(ParamName.ANSWER_ID, answer_id);
    params.put(ParamName.CHOICE_DICTIONARY, choices);
    params.put(ParamName.ANSWERED_TIME, new Date().getTime());
    System.out.print("At this stage 2, the name: ");
    System.out.println(player_id);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public void skipQuestion(String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QAnsweringStateEvent.SKIP);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public HashMap<String, Object> getResults(String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QStatisticsStateEvent.SEND_RESULT);
    params.put(ParamName.CHOICE_DICTIONARY, choices);
    params.put(ParamName.SCORE_DICTIONARY, scores);
    params.put(ParamName.GAME_DATA_DICTIONARY, gameData);
    params.put(ParamName.GRADING_STRATEGY, game.getStrategy());
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
    return result;
  }

  public HashMap<String, Object> getRanking(String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.SCORE_DICTIONARY, scores);
    params.put(ParamName.EVENT, GameConfig.GameRankingStateEvent.GET_RANKING);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
    return result;
  }
}
