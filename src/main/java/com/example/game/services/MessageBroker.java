package com.example.game.services;

import com.example.game.dto.RequestMessage;
import com.example.game.dto.RoutingEnd;
import com.example.game.routing.ActionBaseRule;
import com.example.game.routing.InfoBaseRule;
import com.example.game.routing.RoutingRule;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageBroker {
  private ArrayList<RoutingRule> routingRules;
  private GInfoService gInfoService;
  private GActionService gActionService;

  @Autowired
  public MessageBroker(GInfoService gInfoService, GActionService gActionService) {
    this.gInfoService = gInfoService;
    this.gActionService = gActionService;
    routingRules = new ArrayList<>();
    routingRules.add(new InfoBaseRule());
    routingRules.add(new ActionBaseRule());
  }

  public void addRoutingRule(RoutingRule rule) {
    routingRules.add(rule);
  }

  public void routeMessage(RequestMessage message) {
    for (RoutingRule rule : routingRules) {
      if (rule.canApplyRule(message)) {
        RoutingEnd end = rule.excute(message);
        if (end.getDestination().equals("GInfoService")) {
          String serviceName = end.getServiceName();
          switch (serviceName) {
            default -> {

            }
          }
        } else if (end.getDestination().equals("GActionService")) {
          String serviceName = end.getServiceName();
          switch (serviceName) {
            default -> {

            }
          }
        }
      }
    }
  }
}
