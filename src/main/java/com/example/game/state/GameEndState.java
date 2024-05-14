package com.example.game.state;

import com.example.game.config.GameConfig;

public class GameEndState extends GameState {

  @Override
  public void toNextState(String event) {
    switch (event) {
      case GameConfig.GameEndStateEvent.GET_FINAL_RANK -> {
        System.out.println("At GameEndState, game end event occurred.");
        gameExecutor.setState(new EmptyGameState());
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
