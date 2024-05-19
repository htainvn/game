package com.example.game.datacontainer.implementations;

import com.example.game.datacontainer.interfaces.IPlayerDictionary;
import com.example.game.entities.Player;
import com.example.game.redis.RedisService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDictionary implements IPlayerDictionary {
  LoadingCache<String, ArrayList<Player>> players = CacheBuilder.newBuilder()
    .maximumSize(100)
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .build(
      new CacheLoader<String, ArrayList<Player>>() {
        public @NotNull ArrayList<Player> load(@NotNull String party_id)  {
          HashSet<Object> playersSet = redis.findArray(party_id);
          try {
            ArrayList<Player> playerList = new ArrayList<>();
            for (Object player : playersSet) {
              playerList.add((Player) player);
            }
            players.put(party_id, playerList);
            return playerList;
          } catch (Exception e) {
            return new ArrayList<>();
          }
        }
      }
    );

  private final RedisService redis;

  @Autowired
  public PlayerDictionary(RedisService redis) {
    this.redis = redis;
  }

  @Override
  public void registerPlayer(String party_id, Player player) {
    try {
      ArrayList<Player> playerList = players.get(party_id);
      playerList.add(player);
      redis.append(party_id, player);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Player getPlayer(String party_id, String name) {
    try {
      ArrayList<Player> playerList = players.get(party_id);
      for (Player player : playerList) {
        if (player.getName().equals(name)) {
          return player;
        }
      }
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public boolean containsPlayer(String party_id, String name) {
    try {
      ArrayList<Player> playerList = players.get(party_id);
      for (Player player : playerList) {
        if (player.getName().equals(name)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public ArrayList<Player> getAllPlayers(String party_id) {
    try {
      return players.get(party_id);
    } catch (Exception e) {
      return null;
    }
  }
}
