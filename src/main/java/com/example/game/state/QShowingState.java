package com.example.game.state;

import com.example.game.config.GameConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QShowingState extends GameState {

  public QShowingState() {
    super();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    executor.execute(() -> {
      try {
        Thread.sleep(GameConfig.questionShowingTime * 1000);
        this.toNextState("timeout");
      } catch (InterruptedException e) {
        log.atTrace().log("Thread interrupted");
      }
    });
  }

  @Override
  public void toNextState(String event) {
    switch (event) {
      case "timeout" -> gameExecutor.setState(new QStatisticsState());
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
