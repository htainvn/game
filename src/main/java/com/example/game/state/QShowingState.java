package com.example.game.state;

import com.example.game.config.GameConfig;

import com.example.game.entities.Game;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QShowingState extends GameState {

  public QShowingState() {

    super();
  }

  @Override
  public void toNextState(String event) {
    switch (event) {
      case GameConfig.QShowingStateEvent.TIME_OUT -> {
        log.info("At QShowingState, time out event occurred.");
        GameState newQAnsweringState = new QAnsweringState();
        gameExecutor.setState(newQAnsweringState);
        newQAnsweringState.setGameExecutor(gameExecutor);
      }
      case GameConfig.QShowingStateEvent.ANSWER_QUESTION -> {
        log.info("At QShowingState, answer question event occurred. Moving to QAnsweringState.");
        gameExecutor.setState(new QAnsweringState());
        HashMap<String, Object> params;
        params = new HashMap<>();
        params.put("event", GameConfig.QAnsweringStateEvent.INITIALIZE);
        gameExecutor.execute(params);
      }
      case GameConfig.QShowingStateEvent.SHOW_QUESTION -> {
          log.info("At QShowingState, show question event occurred.");
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
