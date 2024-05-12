package com.example.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameChoiceCacheObject {
  private GameChoice lastKnownValue;
  private Long lastUpdatedTime;
  private Long timeToLive;

  public GameChoice get() {
    return lastKnownValue;
  }
}
