package com.example.game.redis;

import com.example.game.dto.GameChoice;
import com.example.game.entities.GameQuizDto;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {
  private final IRedisRepo redisRepo;
  @Autowired
  public RedisService(IRedisRepo redisRepo) {
    this.redisRepo = redisRepo;
  }

  public void append(String key, Object value) {
    redisRepo.appendArray(key, value);
  }

  public void increment(String key, int value) {
    redisRepo.increaseValue(key, value);
  }

  public void decrease(String key, int value) {
    redisRepo.decreaseValue(key, value);
  }

  public HashSet<Object> findArray(String key) {
    return redisRepo.findArray(key);
  }

  public Long findScore(String key) {
    return redisRepo.findValue(key);
  }

  public void save(String key, Object value) {
    Gson gson = new Gson();
    String json = gson.toJson(value);
    System.out.println("Saving to Redis: " + json);
    try {
      redisRepo.saveHash(key, new RedisObject(StringCompressor.compress(json), 120));
    } catch (Exception e) {
      log.atTrace().log("Error saving to Redis: " + e.getMessage());
    }
  }

  public Object find(String key, Class wanted_class) {
    RedisObject redisObject = redisRepo.findHash(key);
    if (redisObject == null) {
      return null;
    }
    Gson gson = new Gson();
    try {
      if (wanted_class.equals(GameQuizDto.class)) {
        String json = StringCompressor.decompress(redisObject.getValue());
        System.out.println("Finding from Redis: " + json);
        return gson.fromJson(json, GameQuizDto.class);
      } else if (wanted_class.equals(GameChoice.class)) {
        String json = StringCompressor.decompress(redisObject.getValue());
        System.out.println("Finding from Redis: " + json);
        return gson.fromJson(json, GameChoice.class);
      }
      return null;
    }
    catch (Exception e) {
      log.atTrace().log("Error finding from Redis: " + e.getMessage());
      return null;
    }
  }
}
