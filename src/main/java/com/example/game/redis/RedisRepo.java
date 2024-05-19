package com.example.game.redis;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRepo implements IRedisRepo {
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisRepo(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void saveHash(String key, RedisObject obj) {
    long ttl = obj.getTtl();
    redisTemplate.opsForHash().put(key, key, obj);
    if (ttl != -1) {
      redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }
  }

  @Override
  public RedisObject findHash(String key) {
    Object obj = redisTemplate.opsForHash().get(key, key);
    if (obj == null) {
      return null;
    }
    return (RedisObject) obj;
  }

  @Override
  public void deleteHash(String key) {
    redisTemplate.opsForHash().delete(key, key);
  }

  @Override
  public void appendArray(String key, Object value) {
    redisTemplate.opsForSet().add(key, value);
  }

  @Override
  public HashSet<Object> findArray(String key) {
    return new HashSet<>(Objects.requireNonNull(redisTemplate.opsForSet().members(key)));
  }

  @Override
  public void saveValue(String key, Long value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void increaseValue(String key, int value) {
    redisTemplate.opsForValue().increment(key, value);
  }

  @Override
  public void decreaseValue(String key, int value) {
    redisTemplate.opsForValue().increment(key, -value);
  }

  @Override
  public Long findValue(String key) {
    return (Long) redisTemplate.opsForValue().get(key);
  }
}
