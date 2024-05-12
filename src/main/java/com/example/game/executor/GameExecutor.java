package com.example.game.executor;

import com.example.game.config.GameConfig.LobbyStateEvent;
import com.example.game.state.GameEndState;
import com.example.game.state.GameRankingState;
import com.example.game.state.GameState;
import com.example.game.state.LobbyState;
import com.example.game.state.QAnsweringState;
import com.example.game.state.QShowingState;
import com.example.game.state.QStatisticsState;
import com.example.game.strategies.GradingStrategy;
import com.example.game.visitor.Visitor;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public abstract class GameExecutor {
  @Getter
  @Setter
  private String gameID;
  private Integer questionCount;
  private Integer currentQuestionIndex;
  private Integer currentQuestionTime;
  @Getter
  private String accessCode;
  @Setter
  private GameState state;
  @Setter
  private GradingStrategy strategy;

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

    }
    else if (state instanceof QAnsweringState) {

    }
    else if (state instanceof QStatisticsState) {

    }
    else if (state instanceof GameRankingState) {

    }
    else if (state instanceof GameEndState) {

    }
    else {
      throw new RuntimeException("Invalid state");
    }
    result.put("state", state);
    result.put("status", "success");
    return result;
  }

  private void executeOnlyInLobbyState(String event, HashMap<String, Object> params) {

    if (state.IfAccept(event)) {
      switch (event) {
        case LobbyStateEvent.GET_ACCESS_CODE -> {
          // do something
        }
        case LobbyStateEvent.REGISTER -> {
          // do something
        }
      }
    }
  }

  private void executeOnlyInQShowingState(String event, HashMap<String, Object> params) {
    if (state.IfAccept(event)) {

    }
  }

  private void executeOnlyInQAnsweringState(String event, HashMap<String, Object> params) {
    if (state.IfAccept(event)) {

    }
  }

  private void executeOnlyInQStatisticsState(String event, HashMap<String, Object> params) {
    if (state.IfAccept(event)) {

    }
  }

  private void executeOnlyInGameRankingState(String event, HashMap<String, Object> params) {
    if (state.IfAccept(event)) {

    }
  }

  private void executeOnlyInGameEndState(String event, HashMap<String, Object> params) {
    if (state.IfAccept(event)) {

    }
  }

  public void setAccessCode(String accessCode) {
    if (accessCode.length() != 7) {
      throw new IllegalArgumentException("Invalid access code");
    }
    this.accessCode = accessCode;
  }

  public abstract void accept(Visitor visitor);
}