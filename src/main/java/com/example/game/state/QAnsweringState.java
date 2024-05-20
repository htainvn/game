package com.example.game.state;

import com.example.game.config.GameConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class QAnsweringState extends GameState {

  public QAnsweringState() {
//    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
//    executor.execute(() -> {
//      try {
//        Thread.sleep(time * 1000L);
//        this.toNextState(GameConfig.QAnsweringStateEvent.TIME_OUT);
//      } catch (InterruptedException e) {
//        System.out.println("Thread interrupted");
//      }
//    });
  }

  @Override
  public void toNextState(String event) {
    switch (event) {
      case GameConfig.QAnsweringStateEvent.TIME_OUT -> {
        System.out.println("At QAnsweringState, timeout event occurred. Moving to QStatisticsState.");
        GameState newQStatisticsState = new QStatisticsState();
        gameExecutor.setState(newQStatisticsState);
        newQStatisticsState.setGameExecutor(gameExecutor);
      }
      case GameConfig.QAnsweringStateEvent.SEND_CHOICE -> {
        System.out.println("At QAnsweringState, send choice event occurred.");
      }
      case GameConfig.QAnsweringStateEvent.EXCEED_MAX_CORRECT -> {
        System.out.println("At QAnsweringState, exceed max correct event occurred.");
        GameState newQStatisticsState = new QStatisticsState();
        gameExecutor.setState(newQStatisticsState);
        newQStatisticsState.setGameExecutor(gameExecutor);
      }
      case GameConfig.QAnsweringStateEvent.SKIP -> {
        System.out.println("At QAnsweringState, skip event occurred.");
        GameState newQStatisticsState = new QStatisticsState();
        gameExecutor.setState(newQStatisticsState);
        newQStatisticsState.setGameExecutor(gameExecutor);
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
