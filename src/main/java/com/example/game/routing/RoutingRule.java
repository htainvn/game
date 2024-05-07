package com.example.game.routing;

import com.example.game.dto.RequestMessage;
import com.example.game.dto.RoutingEnd;

public interface RoutingRule {
  public Boolean canApplyRule(RequestMessage message);
  public RoutingEnd excute(RequestMessage message);
}
