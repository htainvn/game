package com.example.game.redis;

import com.example.game.entities.GameQuizDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@NoArgsConstructor
@AllArgsConstructor
@RedisHash
public class RedisObject {
  @Id
  private String key;
  @Getter
  private String value;
  @TimeToLive
  private int ttl;
}
