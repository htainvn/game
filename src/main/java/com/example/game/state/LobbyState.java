package com.example.game.state;

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
      case "start_game" -> {
        System.out.println("Game started");
        gameExecutor.setState(new LobbyState());
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
