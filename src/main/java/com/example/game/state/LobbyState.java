package com.example.game.state;

import com.example.game.config.GameConfig;

import java.util.ArrayList;

public class LobbyState extends GameState {

  public LobbyState() {
    super.Register(LobbyState.class.getName());
    acceptedRequests.add("get_access_code");
    acceptedRequests.add("register");
    acceptedRequests.add("start_game");
  }

  @Override
  public void toNextState(String event) {
    switch (event) {
      case GameConfig.LobbyStateEvent.START_GAME -> {
        System.out.println("Game started");
        GameState newQShowingState = new QShowingState();
        gameExecutor.setState(newQShowingState);
        newQShowingState.setGameExecutor(gameExecutor);
        gameExecutor.setCurrentQuestionID(0L);
      }
      case GameConfig.LobbyStateEvent.GET_ACCESS_CODE -> {
        System.out.println("Access code is: " + gameExecutor.getAccessCode());
      }
        case GameConfig.LobbyStateEvent.REGISTER -> {
          System.out.println("Player registered");
        }
    }
  }

  @Override
  public void toPreviousState(String event) {

  }

  @Override
  public int getIndex() {
    return super.getStateIndex(LobbyState.class.getName());
  }

  @Override
  public boolean IfTransitionHappens(String event) {
    return false;
  }
}
