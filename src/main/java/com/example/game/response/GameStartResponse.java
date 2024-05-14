package com.example.game.response;

import com.example.game.config.GameConfig;

public class GameStartResponse extends Response {
  private String gameID;

  public GameStartResponse() {
    super();
  }

  public GameStartResponse(String gameID) {
    super(true, GameConfig.GAME_STARTED_CODE, "Game started successfully");
    this.gameID = gameID;
  }
}
