package com.example.game.services;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.EmptyGameStateEvent;
import com.example.game.config.GameConfig.GameEndStateEvent;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.datacontainer.*;
import com.example.game.dto.OriginalQuizDto;
import com.example.game.entities.GameQuizDto;
import com.example.game.executor.GameExecutor;
import com.example.game.factory.GameFactory;
import com.example.game.helper.RandomCode;
import com.google.gson.Gson;
import java.util.HashMap;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Facade
@Service
public class GameService {
  private HashMap<String, GameExecutor> games = new HashMap<>();
  private TempPlayerDictionary players;
  private TempChoiceDictionary choices;
  private TempScoreDictionary scores;

  private DataService dataService;

  @Autowired
  public GameService(DataService dataService) {
    this.dataService = dataService;
  }

  public Pair<String, String> /* <party_id, access_code> */ createGame(
      String quiz_id,
      String host_id,
      String gameFlowMode,
      String gradingStrategy
  ) {
    GameExecutor game = GameFactory.createGame(gameFlowMode, gradingStrategy);
    try {
      String quizData = new RestService().get("http://localhost:8080/quiz/" + quiz_id);
      Gson gson = new Gson();
      OriginalQuizDto originalQuizDto = gson.fromJson(quizData, OriginalQuizDto.class);
      GameQuizDto gameQuizDto = new GameQuizDto(originalQuizDto);
      HashMap<String, Object> params = new HashMap<>();
      params.put(ParamName.EVENT, EmptyGameStateEvent.BIND);
      params.put(ParamName.DATA, gameQuizDto);
      HashMap<String, Object> result = game.execute(params);
      if (!result.get("status").equals("success")) {
        throw new Exception("Failed to create game");
      }
      String accessCode = RandomCode.generate(GameConfig.CODE_LENGTH);
      game.setAccessCode(accessCode);
      games.put(game.getGameID(), game);
      dataService.store(gameQuizDto);
    } catch (Exception e) {
      throw new UnsupportedOperationException();
    }
    return Pair.with(game.getGameID(), game.getAccessCode());
  }

  public void startGame(
      String party_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.LobbyStateEvent.START_GAME);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
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
      String player_name
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.LobbyStateEvent.REGISTER);
    params.put(ParamName.NAME, player_name);
    params.put(ParamName.PLAYER_DICTIONARY, players);
    HashMap<String, Object> result = game.execute(params);
//    if (!result.get("status").equals("success")) {
//      throw new UnsupportedOperationException();
//    }
    return Pair.with(result.get("status").toString(), result.get("player_id").toString());
  }

  public HashMap<String, Object> showQuestion(
      String party_id) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.QShowingStateEvent.SHOW_QUESTION);
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
