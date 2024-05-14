package com.example.game.state;

import com.example.game.config.GameConfig;

public class GameRankingState extends GameState {
  private boolean isFinal;

  @Override
  public void toNextState(String event) {
    switch (event) {
      case GameConfig.GameRankingStateEvent.GET_RANKING -> {
        System.out.println("At GameRankingState, get ranking event occurred.");
        gameExecutor.setState(new QShowingState());
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
