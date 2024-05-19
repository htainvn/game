package com.example.game.executor;

import com.example.game.services.DataService;
import com.example.game.visitor.QAnsweringStateVisitor;

import java.util.HashMap;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

@Component
public class MaxCorrectGameExecutor extends GameExecutor {

  public MaxCorrectGameExecutor(
      DataService dataService,
      SimpUserRegistry simpUserRegistry
  ) {
    super(dataService, simpUserRegistry);
  }

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
