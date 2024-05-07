package com.example.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
public class RoutingEnd {
  @Getter
  private String destination;

  @Getter
  private String serviceName;

  private VariableDictionary variables;

  public Object get(String key, Object value) {
    return this.variables.get(key);
  }
}
