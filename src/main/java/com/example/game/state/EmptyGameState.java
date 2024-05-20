package com.example.game.state;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.EmptyGameStateEvent;

public class EmptyGameState extends GameState {

  @Override
  public void toNextState(String event) {
    switch (event) {
      case EmptyGameStateEvent.INITIALIZED -> {
        GameState newLobbyState = new LobbyState();
        gameExecutor.setState(newLobbyState);
        newLobbyState.setGameExecutor(gameExecutor);
      }
    }
  }

  @Override
  public void toPreviousState(String event) {

  }

  @Override
  public int getIndex() {
    return 0;
  }

  @Override
  public boolean IfTransitionHappens(String event) {
    return false;
  }
}
