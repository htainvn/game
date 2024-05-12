package com.example.game.services;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.EmptyGameStateEvent;
import com.example.game.config.GameConfig.GameEndStateEvent;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.dto.OriginalQuizDto;
import com.example.game.entities.Game;
import com.example.game.entities.GameQuizDto;
import com.example.game.executor.GameExecutor;
import com.example.game.factory.GameFactory;
import com.example.game.helper.RandomCode;
import com.example.game.state.EmptyGameState;
import com.google.gson.Gson;
import java.util.HashMap;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Facade
@Service
public class GameService {
  private HashMap<String, GameExecutor> games = new HashMap<>();

  private DataService dataService;

  @Autowired
  public GameService(DataService dataService) {
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

  public void endGame(
      String party_id
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameEndStateEvent.GET_FINAL_RANK);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public void registerPlayerToGame(
      String party_id,
      String player_name
  ) {
    GameExecutor game = games.get(party_id);
    HashMap<String, Object> params = new HashMap<>();
    params.put(ParamName.EVENT, GameConfig.LobbyStateEvent.REGISTER);
    params.put(ParamName.NAME, player_name);
    HashMap<String, Object> result = game.execute(params);
    if (!result.get("status").equals("success")) {
      throw new UnsupportedOperationException();
    }
  }

  public void receive(
      String party_id,
      String player_id,
      String answer
  ) {
    throw new UnsupportedOperationException();
  }
}
