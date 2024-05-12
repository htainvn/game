package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QEndResponse extends Response {
  private String gameID;
  private String statistics;

  public QEndResponse() {
    super();
  }

  public QEndResponse(String gameID, String statistics) {
    super(true, GameConfig.QUESTION_ENDED_CODE, "Question ended successfully");
    this.gameID = gameID;
    this.statistics = statistics;
  }
}
