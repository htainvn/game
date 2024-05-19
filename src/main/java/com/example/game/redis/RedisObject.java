package com.example.game.redis;

import com.example.game.entities.GameQuizDto;
import com.google.gson.Gson;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RedisObject implements Serializable {

  private String objClass;
  private String value;
  private long ttl;

  public RedisObject(String objClass, Object obj, int ttl) {
    this.objClass = objClass;
    Gson gson = new Gson();
    this.value = gson.toJson(obj);
    this.ttl = ttl;
  }

  public RedisObject(Object obj, long ttl) {
    Gson gson = new Gson();
    if (obj instanceof String) {
      this.value = (String) obj;
    }
    else {
      this.value = gson.toJson(obj);
    }
    this.ttl = ttl;
  }
}
