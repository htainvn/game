package com.example.game.state;

import com.example.game.executor.GameExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class GameState {
  private final HashMap<String, Integer> stateMap = new HashMap<>();
  protected final ArrayList<String> acceptedRequests = new ArrayList<>();
  protected GameExecutor gameExecutor;
  protected void Register(String stateType) {
    if (!stateMap.containsKey(stateType)) {
      stateMap.put(stateType, stateMap.size() + 1);
    }
  }
  protected int getStateIndex(String stateType) {
    return stateMap.get(stateType);
  }
  public boolean IfAccept(String req) {
    return acceptedRequests.contains(req);
  }
  public abstract void toNextState(String event);
  public abstract void toPreviousState(String event);
  public abstract int getIndex();
  public void setGameExecutor(GameExecutor gameExecutor) {
    this.gameExecutor = gameExecutor;
  }
  public abstract boolean IfTransitionHappens(String event);
}
