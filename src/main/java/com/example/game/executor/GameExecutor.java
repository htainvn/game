package com.example.game.executor;

import com.example.game.config.GameConfig;
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

public abstract class GameExecutor {
  @Getter
  @Setter
  private String gameID;
  private Integer questionCount;
  @Getter
  private String currentQuestionID;
  @Getter
  private Integer currentQuestionCnt;
  @Getter
  private String accessCode;
  @Getter
  @Setter
  private GameState state;
  @Getter
  @Setter
  private GradingStrategy strategy;
  protected Visitor visitor;
  @Getter
  private ThreadPoolExecutor timeoutThread = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
  // private HashMap<String, Object> players;
  // private HashMap<String, Object> questions;

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

    if (state instanceof LobbyState) {
      executeOnlyInLobbyState(event, params);
    }
    else if (state instanceof QShowingState) {
      executeOnlyInQShowingState(event, params);
    }
    else if (state instanceof QAnsweringState) {
      executeOnlyInQAnsweringState(event, params);
    }
    else if (state instanceof QStatisticsState) {
      executeOnlyInQStatisticsState(event, params);
    }
    else if (state instanceof GameRankingState) {
      executeOnlyInGameRankingState(event, params);
    }
    else if (state instanceof GameEndState) {
      executeOnlyInGameEndState(event, params);
    }
    else {
      throw new RuntimeException("Invalid state");
    }
    result.put("state", state);
    result.put("status", "success");
    return result;
  }

  private HashMap<String, Object> executeOnlyInLobbyState(String event, HashMap<String, Object> params) {
    this.accept(new LobbyStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithTimeUpGame(event, params);
    this.kick();
    visitor.getKicked();
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