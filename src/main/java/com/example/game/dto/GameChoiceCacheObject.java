package com.example.game.dto;

import com.example.game.config.GameConfig;
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

  public GameChoiceCacheObject(GameChoice value) {
    this.lastKnownValue = value;
    this.lastUpdatedTime = System.currentTimeMillis();
    this.timeToLive = GameConfig.GAME_CHOICE_CACHE_TIME;
  }

  public GameChoice get() {
    return lastKnownValue;
  }

  public boolean isExpired() {
    return System.currentTimeMillis() > lastUpdatedTime + timeToLive;
  }
}
