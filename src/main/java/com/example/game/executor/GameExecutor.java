package com.example.game.executor;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.GameSettingsModel;
import com.example.game.services.DataService;
import com.example.game.state.EmptyGameState;
import com.example.game.state.GameEndState;
import com.example.game.state.GameRankingState;
import com.example.game.state.GameState;
import com.example.game.state.LobbyState;
import com.example.game.state.QAnsweringState;
import com.example.game.state.QShowingState;
import com.example.game.state.QStatisticsState;
import com.example.game.strategies.GradingStrategy;
import com.example.game.visitor.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;

public abstract class GameExecutor {
  @Getter
  @Setter
  private String gameID;
  @Getter
  @Setter
  private Integer questionCount;
  @Getter
  @Setter
  private Long currentQuestionID;
  @Getter
  @Setter
  private Long currentQuestionCnt;
  @Getter
  private String accessCode;
  @Getter
  @Setter
  private GameState state;
  @Getter
  @Setter
  private GradingStrategy strategy;
  @Getter
  private ThreadPoolExecutor timeoutThread = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
  // private HashMap<String, Object> players;
  // private HashMap<String, Object> questions;
  private final DataService dataService;
  private final SimpUserRegistry simpUserRegistry;
  protected Visitor visitor;
  private GameSettingsModel settings;
  @Autowired
  protected GameExecutor(
      DataService dataService,
      SimpUserRegistry simpUserRegistry
  ) {
    this.dataService = dataService;
    this.simpUserRegistry = simpUserRegistry;
  }

  public HashMap<String, Object>  execute(
      HashMap<String, Object> params
  ) {
    HashMap<String, Object> result = new HashMap<>();
    String event = (String) params.get("event");
    if (event == null) {
      return result;
    }
    if (state.IfAccept(event)) {
      if (state.IfTransitionHappens(event)) {
        state.toNextState(event);
        result.put("state", state);
        result.put("status", "success");
        return result;
      }
    }
    HashMap<String, Object> tmp_result;
    if (state instanceof EmptyGameState) {
      tmp_result = executeOnlyInEmptyGameState(event, params);
    }
    else if (state instanceof LobbyState) {
      tmp_result = executeOnlyInLobbyState(event, params);
    }
    else if (state instanceof QShowingState) {
      tmp_result = executeOnlyInQShowingState(event, params);
    }
    else if (state instanceof QAnsweringState) {
      tmp_result = executeOnlyInQAnsweringState(event, params);
    }
    else if (state instanceof QStatisticsState) {
      tmp_result = executeOnlyInQStatisticsState(event, params);
    }
    else if (state instanceof GameRankingState) {
      tmp_result = executeOnlyInGameRankingState(event, params);
    }
    else if (state instanceof GameEndState) {
      tmp_result = executeOnlyInGameEndState(event, params);
    }
    else {
      throw new RuntimeException("Invalid state");
    }
    tmp_result.forEach((key, value) -> {
      if (key.equals(ParamName.STATUS_PR) && value.equals("failed")) {
        result.put(ParamName.STATUS_PR, value);
      }
      else {
        if (result.containsKey(key)) {
          throw new RuntimeException("Duplicated key");
        }
        else {
          result.put(key, value);
        }
      }
    });
    result.put("state", state);
    result.put(ParamName.STATUS_PR, "success");
    return result;
  }

  private HashMap<String, Object> executeOnlyInEmptyGameState(String event, HashMap<String, Object> params) {
    this.accept(new EmptyGameStateVisitor());
    visitor.getAccepted(this);
    params.put(ParamName.DATA_SERVICE, dataService);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    visitor.getKicked();
    this.kick();
    return result;
  }

  private HashMap<String, Object> executeOnlyInLobbyState(String event, HashMap<String, Object> params) {
    this.accept(new LobbyStateVisitor());
    visitor.getAccepted(this);

    /* params packaging */
    switch (event) {
      case GameConfig.LobbyStateEvent.GET_ACCESS_CODE -> {
        System.out.println("At LobbyState, get access code event occurred.");
        // TO DO
      }
      case GameConfig.LobbyStateEvent.START_GAME -> {
        System.out.println("At LobbyState, start game event occurred.");
        //params.put(ParamName.GAME_DATA_DICTIONARY, );
        // TO DO
      }
      case GameConfig.LobbyStateEvent.REGISTER -> {
        System.out.println("Pushing simpUserRegistry to params");
        params.put(ParamName.SIMP_USER_REGISTRY, simpUserRegistry);
        // TO DO
      }
    }
    /* end of params packaging */

    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    visitor.getKicked();
    this.kick();
    return result;
  }

  private HashMap<String, Object> executeOnlyInQShowingState(String event, HashMap<String, Object> params) {
    this.accept(new QShowingStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    this.kick();
    visitor.getKicked();
    return result;
  }

  protected abstract HashMap<String, Object> executeOnlyInQAnsweringState(String event, HashMap<String, Object> params);

  private HashMap<String, Object> executeOnlyInQStatisticsState(String event, HashMap<String, Object> params) {
    this.accept(new QShowingStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    this.kick();
    visitor.getKicked();
    return result;
  }

  private HashMap<String, Object> executeOnlyInGameRankingState(String event, HashMap<String, Object> params) {
    this.accept(new GameRankingStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    this.kick();
    visitor.getKicked();
    return result;
  }

  private HashMap<String, Object> executeOnlyInGameEndState(String event, HashMap<String, Object> params) {
    this.accept(new GameEndStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    this.kick();
    visitor.getKicked();
    return result;
  }

  public void setAccessCode(String accessCode) {
    if (accessCode.length() != 7) {
      throw new IllegalArgumentException("Invalid access code");
    }
    this.accessCode = accessCode;
  }

  public void accept(Visitor visitor) {
    this.visitor = visitor;
  }

  public void kick() {
    this.visitor = null;
  }

  public boolean isFinal() {
    return Objects.equals(currentQuestionCnt, questionCount);
  }
}