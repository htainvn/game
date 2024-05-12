package com.example.game.state;

public class GameRankingState extends GameState {
  private boolean isFinal;

  @Override
  public void toNextState(String event) {

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
