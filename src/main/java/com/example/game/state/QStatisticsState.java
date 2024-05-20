package com.example.game.state;

import com.example.game.config.GameConfig;
import com.example.game.entities.Game;

public class QStatisticsState extends GameState {
  private boolean isFinal;

    @Override
  public void toNextState(String event) {
      isFinal = gameExecutor.isFinal();
    switch (event) {
      case GameConfig.QStatisticsStateEvent.SEND_RESULT -> {
        System.out.println("At QStatisticsState, send result event occurred. Moving to GameEndState.");
        if (isFinal) {
          GameState newGameEndState = new GameEndState();
          gameExecutor.setState(newGameEndState);
          newGameEndState.setGameExecutor(gameExecutor);
        } else {
          GameState newGameRankingState = new GameRankingState();
          gameExecutor.setState(newGameRankingState);
          newGameRankingState.setGameExecutor(gameExecutor);
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
