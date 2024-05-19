package com.example.game.datacontainer.implementations;

import com.example.game.datacontainer.cachekey.RankingCacheKey;
import com.example.game.datacontainer.cachekey.ScoreCacheKey;
import com.example.game.datacontainer.interfaces.IPlayerDictionary;
import com.example.game.datacontainer.interfaces.IScoreDictionary;
import com.example.game.entities.Player;
import com.example.game.entities.Score;
import com.example.game.redis.RedisService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ScoreDictionary implements IScoreDictionary {

  LoadingCache<ScoreCacheKey, Score> scores = CacheBuilder.newBuilder()
    .maximumSize(17876)
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .build(
      new CacheLoader<ScoreCacheKey, Score>() {
        public @NotNull Score load(@NotNull ScoreCacheKey key) {
          String party_id = key.party_id;
          String player_id = key.player_id;
          Score score = new Score(party_id, player_id, redis.findScore(
            party_id + ":" + player_id
          ));
          scores.put(key, score);
          return score;
        }
      }
    );

  LoadingCache<RankingCacheKey, ArrayList<Score>> ranking = CacheBuilder.newBuilder()
    .maximumSize(100)
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .build(
      new CacheLoader<RankingCacheKey, ArrayList<Score>>() {
        public @NotNull ArrayList<Score> load(@NotNull RankingCacheKey key) {
          ArrayList<Player> players = playerDictionary.getAllPlayers(key.party_id);
          ArrayList<Score> scoresArray = new ArrayList<>();
          try {
            for (Player player : players) {
              scoresArray.add(scores.get(
                  new ScoreCacheKey(key.party_id, player.getName(), key.iteration)
              ));
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          return scoresArray;
        }
      }
    );

  private final RedisService redis;
  private final IPlayerDictionary playerDictionary;

  public ScoreDictionary(RedisService redis, IPlayerDictionary playerDictionary) {
    this.redis = redis;
    this.playerDictionary = playerDictionary;
  }

  @Override
  public Score getScore(String party_id, String player_id, Long iteration) {
    try {
      return scores.get(new ScoreCacheKey(party_id, player_id, iteration));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public HashMap<String, Score> getScore(String party_id, Long iteration) {
    try {
      ArrayList<Player> players = playerDictionary.getAllPlayers(party_id);
      HashMap<String, Score> scoresMap = new HashMap<>();
      for (Player player : players) {
        scoresMap.put(player.getName(), scores.get(
            new ScoreCacheKey(party_id, player.getName(), iteration)
        ));
      }
      return scoresMap;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public HashMap<String, Score> getScoreInParty(String party_id) {
    return null;
  }

  @Override
  public void setScore(String party_id, HashMap<String, Score> score) {
    ArrayList<Score> scoresArray = new ArrayList<>();
    for (String player_id : score.keySet()) {
      scoresArray.add(score.get(player_id));
      redis.save(party_id + ":" + player_id, score.get(player_id).getScore());
    }
  }

  @Override
  public HashMap<String, Score> getRanking(String party_id) {
    return null;
  }

  @Override
  public void refreshRanking(String party_id) {
    scores.cleanUp();
  }


}
