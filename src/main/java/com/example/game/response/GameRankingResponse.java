package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameRankingResponse extends Response {
  private String gameID;
  private String ranking;

  public GameRankingResponse() {
    super();
  }

  public GameRankingResponse(String gameID, String ranking) {
    super(true, GameConfig.GAME_RANKING_CODE, "Game ranking successfully");
    this.gameID = gameID;
    this.ranking = ranking;
  }
}
