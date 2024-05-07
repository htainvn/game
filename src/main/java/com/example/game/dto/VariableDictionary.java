package com.example.game.dto;

import java.util.HashMap;

public class VariableDictionary {
  private HashMap<String, Object> variables;

  public VariableDictionary() {
    this.variables = new HashMap<String, Object>();
  }

  public void put(String key, Object value) {
    this.variables.put(key, value);
  }

  public Object get(String key) {
    return this.variables.get(key);
  }

  public void remove(String key) {
    this.variables.remove(key);
  }
}
