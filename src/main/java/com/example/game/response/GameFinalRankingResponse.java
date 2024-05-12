package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameFinalRankingResponse extends Response {
  private String gameID;
  private String ranking;

  public GameFinalRankingResponse() {
    super();
  }

  public GameFinalRankingResponse(String gameID, String ranking) {
    super(true, GameConfig.GAME_FINAL_RANK_CODE, "Game final ranking successfully");
    this.gameID = gameID;
    this.ranking = ranking;
  }
}
