package com.example.game.visitor;

import com.example.game.executor.GameExecutor;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public abstract class Visitor {
  GameExecutor gameExecutor;

  public void getAccepted(GameExecutor gameExecutor) {
    this.gameExecutor = gameExecutor;
  }

  public void getKicked() {
    this.gameExecutor = null;
  }

  public abstract HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params);

  public abstract HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params);
}
