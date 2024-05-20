package com.example.game.datacontainer.implementations;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.cachekey.GameCacheKey;
import com.example.game.datacontainer.interfaces.IChoiceDictionary;
import com.example.game.datacontainer.interfaces.IPlayerDictionary;
import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.redis.RedisObject;
import com.example.game.redis.RedisRepo;
import com.example.game.redis.RedisService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
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
          RedisObject redisObject = redis.findHash(
              "ach:" + party_id + ":" + qid + ":" + player_id
          );
          if (redisObject == null) {
            return GameChoice.builder()
              .party_id(party_id)
              .qid(qid)
              .player_id(player_id)
              .aid(null)
              .time(null)
              .build();
          }
          GameChoice choice = new Gson().fromJson(redisObject.getValue(), GameChoice.class);
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
  private final RedisRepo redis;
  private final IPlayerDictionary players;

  @Autowired
  public ChoiceDictionary(RedisRepo redis, IPlayerDictionary players) {
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
    System.out.print("At this stage 4, the name: ");
    System.out.println(choice.getPlayer_id());
    redis.saveHash(
        "ach:" + choice.getParty_id() + ":" + choice.getQid() + ":" + choice.getPlayer_id(),
        new RedisObject(choice, 240)
    );
    choices.put(
        new GameCacheKey(choice.getParty_id(), choice.getQid(), choice.getPlayer_id()),
        choice
    );
  }

  @Override
  public @Nullable ArrayList<GameChoice> getChoices(String party_id, Long qid) {
    ArrayList<Player> playersInParty = players.getAllPlayers(party_id);
    ArrayList<GameChoice> choices_arr = new ArrayList<>();
    for (Player player : playersInParty) {
      GameChoice choice = get(party_id, qid, player.getName());
      if (choice != null) {
        choices_arr.add(choice);
      }
      else {
        choices_arr.add(
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
    return choices_arr;
  }

  @Override
  public void removeAllChoicesFromParty(String pid) {

  }
}
