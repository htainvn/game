package com.example.game.routing;

import com.example.game.constant.ReqType;
import com.example.game.dto.RequestMessage;
import com.example.game.dto.RoutingEnd;
import java.util.ArrayList;

public class ActionBaseRule implements RoutingRule {
  private ArrayList<String> valid_actions;

  public ActionBaseRule() {
    this.valid_actions = new ArrayList<String>();
    // Add valid actions
  }

  @Override
  public Boolean canApplyRule(RequestMessage message) {
    return valid_actions.contains(message.getService());
  }

  @Override
  public RoutingEnd excute(RequestMessage message) {
    return RoutingEnd.builder()
        .destination("GActionService")
        .serviceName(message.getService())
        .build();
  }
}
