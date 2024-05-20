package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInitializedResponse extends Response {
  private String gameID;

  private String gameAccessCode;

  public GameInitializedResponse() {
    super();
  }

  public GameInitializedResponse(String gameID, String gameAccessCode) {
    super(true, GameConfig.GAME_INITIALIZED_CODE, "Game initialized successfully");
    this.gameID = gameID;
    this.gameAccessCode = gameAccessCode;
  }

  @Override
  public String toString() {
    return "GameInitializedResponse{" +
        "gameID='" + gameID + "'" +
        ", gameAccessCode='" + gameAccessCode + "'" +
        '}';
  }
}
