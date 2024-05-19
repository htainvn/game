package com.example.game.datacontainer.implementations;

import com.example.game.datacontainer.cachekey.GameCacheKey;
import com.example.game.datacontainer.interfaces.IChoiceDictionary;
import com.example.game.datacontainer.interfaces.IPlayerDictionary;
import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.redis.RedisService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChoiceDictionary implements IChoiceDictionary {

  LoadingCache<GameCacheKey, GameChoice> choices = CacheBuilder.newBuilder()
    .maximumSize(17876)
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .build(
      new CacheLoader<GameCacheKey, GameChoice>() {
        public @NotNull GameChoice load(@NotNull GameCacheKey key) {
          String party_id = key.party_id;
          Long qid = key.qid;
          String player_id = key.player_id;
          GameChoice choice = (GameChoice) redis.find(
              party_id + ":" + qid + ":" + player_id,
              GameChoice.class
          );
          if (choice == null) {
            return GameChoice.builder()
              .party_id(party_id)
              .qid(qid)
              .player_id(player_id)
              .aid(null)
              .time(null)
              .build();
          }
          choices.put(key, choice);
          return choice;
        }
      }
    );
  private final RedisService redis;
  private final IPlayerDictionary players;

  @Autowired
  public ChoiceDictionary(RedisService redis, IPlayerDictionary players) {
    this.redis = redis;
    this.players = players;
  }

  @Override
  public @Nullable GameChoice get(String party_id, Long qid, String player_id) {
    try {
      GameChoice obj = choices.get(new GameCacheKey(party_id, qid, player_id));
      if (obj.getAid() == null) {
        return null;
      }
      return obj;
    } catch (ExecutionException e) {
      return null;
    }
  }

  @Override
  public void addChoice(GameChoice choice) {
    redis.save(
        choice.getParty_id() + ":" + choice.getQid() + ":" + choice.getPlayer_id(),
        choice
    );
    choices.put(
        new GameCacheKey(choice.getParty_id(), choice.getQid(), choice.getPlayer_id()),
        choice
    );
  }

  @Override
  public @Nullable ArrayList<GameChoice> getChoices(String party_id, Long qid) {
    ArrayList<Player> playersInParty = players.getAllPlayers(party_id);
    ArrayList<GameChoice> choices = new ArrayList<>();
    for (Player player : playersInParty) {
      GameChoice choice = get(party_id, qid, player.getName());
      if (choice != null) {
        choices.add(choice);
      }
      else {
        choices.add(
            GameChoice.builder()
              .party_id(party_id)
              .qid(qid)
              .player_id(player.getName())
              .aid(null)
              .time(null)
              .build()
        );
      }
    }
    return choices;
  }

  @Override
  public void removeAllChoicesFromParty(String pid) {

  }
}
