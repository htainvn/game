package com.example.game.datacontainer.cachekey;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ScoreCacheKey {
  public String party_id;
  public String player_id;
  public Long iteration;
}
