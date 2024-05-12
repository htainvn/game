package com.example.game.redis;

import com.example.game.entities.GameQuizDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class RedisService {
  private final RedisRepo redisRepo;
  @Autowired
  public RedisService(RedisRepo redisRepo) {
    this.redisRepo = redisRepo;
  }
  public void save(String key, Object value) {
    Gson gson = new Gson();
    String json = gson.toJson(value);
    System.out.println("Saving to Redis: " + json);
    try {
      redisRepo.save(new RedisObject(key, StringCompressor.compress(json), 120));
    } catch (Exception e) {
      log.atTrace().log("Error saving to Redis: " + e.getMessage());
    }
  }

  public GameQuizDto find(String key) {
    RedisObject redisObject = redisRepo.findById(key).orElse(null);
    if (redisObject == null) {
      return null;
    }
    Gson gson = new Gson();
    try {
      String json = StringCompressor.decompress(redisObject.getValue());
      System.out.println("Finding from Redis: " + json);
      return gson.fromJson(json, GameQuizDto.class);
    }
    catch (Exception e) {
      log.atTrace().log("Error finding from Redis: " + e.getMessage());
      return null;
    }
  }
}
