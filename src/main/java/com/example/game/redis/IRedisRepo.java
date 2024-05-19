package com.example.game.redis;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;

public interface IRedisRepo {
  void saveHash(String key, RedisObject obj);

  @Nullable RedisObject findHash(String key);

  void deleteHash(String key);

  void appendArray(String key, Object value);

  HashSet<Object> findArray(String key);

  void saveValue(String key, Long value);

  void increaseValue(String key, int value);

  void decreaseValue(String key, int value);

  Long findValue(String key);
}