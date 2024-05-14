package com.example.game.executor;

import com.example.game.visitor.QAnsweringStateVisitor;

import java.util.HashMap;

public class MaxCorrectGameExecutor extends GameExecutor {

  @Override
  protected HashMap<String, Object> executeOnlyInQAnsweringState(String event, HashMap<String, Object> params) {
    this.accept(new QAnsweringStateVisitor());
    visitor.getAccepted(this);
    HashMap<String, Object> result = visitor.doWithMaxCorrectGame(event, params);
    this.kick();
    visitor.getKicked();
    return result;
  }
}
