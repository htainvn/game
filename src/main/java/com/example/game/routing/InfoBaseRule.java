package com.example.game.routing;

import com.example.game.dto.RequestMessage;
import com.example.game.dto.RoutingEnd;
import java.util.ArrayList;

public class InfoBaseRule implements RoutingRule {
  private ArrayList<String> valid_action;

  public InfoBaseRule() {
    this.valid_action = new ArrayList<String>();
    // Add valid actions
  }

  @Override
  public Boolean canApplyRule(RequestMessage message) {
    return valid_action.contains(message.getService());
  }

  @Override
  public RoutingEnd excute(RequestMessage message) {
    return RoutingEnd.builder()
        .destination("GInfoService")
        .serviceName(message.getService())
        .build();
  }
}
