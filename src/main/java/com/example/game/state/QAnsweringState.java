package com.example.game.state;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class QAnsweringState extends GameState {

  public QAnsweringState(int time) {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    executor.execute(() -> {
      try {
        Thread.sleep(time * 1000L);
        this.toNextState("timeout");
      } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
      }
    });
  }

  @Override
  public void toNextState(String event) {
    switch (event) {
      case "timeout" -> {
        System.out.println("At QAnsweringState, timeout event occurred. Moving to QStatisticsState.");
        gameExecutor.setState(new QStatisticsState());
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
