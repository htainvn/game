package com.example.game.state;

import com.example.game.config.GameConfig;

public class QStatisticsState extends GameState {
  private boolean isFinal;

    @Override
  public void toNextState(String event) {
      isFinal = gameExecutor.isFinal();
    switch (event) {
      case GameConfig.QStatisticsStateEvent.SEND_RESULT -> {
        System.out.println("At QStatisticsState, send result event occurred. Moving to GameEndState.");
        if (isFinal) {
          gameExecutor.setState(new GameEndState());
        } else {
          gameExecutor.setState(new GameRankingState());
        }
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
