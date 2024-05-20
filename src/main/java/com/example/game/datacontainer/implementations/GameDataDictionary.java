package com.example.game.datacontainer.implementations;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.DataServiceType;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.cor.Responsibility;
import com.example.game.cor.Validation;
import com.example.game.datacontainer.cachekey.GameDataCacheKey;
import com.example.game.datacontainer.interfaces.IGameDataDictionary;
import com.example.game.entities.GameQuizDto;
import com.example.game.model.GQuestionModel;
import com.example.game.model.GameSettingsModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.CoRRequest;
import com.example.game.redis.RedisObject;
import com.example.game.redis.RedisRepo;
import com.example.game.redis.RedisService;
import com.example.game.services.DataService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameDataDictionary implements IGameDataDictionary {

  private final DataService dataService;

  private final RedisRepo redis;

  private Responsibility chain = new Validation();

  protected LoadingCache<String, GameSettingsModel> settings = CacheBuilder.newBuilder()
      .maximumSize(17876)
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .build(
          new CacheLoader<String, GameSettingsModel>() {
            public @NotNull GameSettingsModel load(@NotNull String party_id) {
              Gson gson = new Gson();
//              try {
                String json = Objects.requireNonNull(redis.findHash(party_id + ":s:e:t"))
                    .getValue();
                GameSettingsModel settingsObj = gson.fromJson(json, GameSettingsModel.class);
                settings.put(party_id, settingsObj);
                return settingsObj;
//              } catch (NullPointerException e) {
//                return new GameSettingsModel();
//              }
            }
          }
      );

  protected LoadingCache<GameDataCacheKey, QuestionModel> questions = CacheBuilder.newBuilder()
    .maximumSize(17876)
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .build(
      new CacheLoader<GameDataCacheKey, QuestionModel>() {
        public @NotNull QuestionModel load(@NotNull GameDataCacheKey key) {
          String party_id = key.party_id;
          Long qid = key.qid;
          CoRRequest request;
          QuestionModel result = null;
          try {
            request = new CoRRequest();
            GameSettingsModel settingsObj = null;
            if (settings != null) {
              settingsObj = settings.get(party_id);
              if (settingsObj.isAnswer_randomized()) {
                request.putReq(ParamName.COR_ARANDOM, true);
              }
            }
            else {
              request = new CoRRequest();
            }
            request.putReq(ParamName.COR_ARANDOM,
                settingsObj != null && settingsObj.isAnswer_randomized());
            request.putReq(ParamName.COR_FETCH, true);
            request.putParam(ParamName.DATA_SERVICE, dataService);
            request.putParam(ParamName.PARTY_ID, party_id);
            request.putParam(ParamName.QUESTION_ID, qid);
            request.putParam(ParamName.NEXT_QUESTION_ID, getNextQuestionId(party_id));
            result = chain.handleRequest(request);
            questions.put(new GameDataCacheKey(party_id, qid), result);
          } catch (Exception e) {
            request = new CoRRequest();
          }
          if (result == null) {
            return new QuestionModel();
          }
          return result;
        }
      }
    );

  @Autowired
  public GameDataDictionary(DataService dataService, RedisRepo redis) {
    this.dataService = dataService;
    this.redis = redis;
  }

  @Override
  public QuestionModel get(String party_id, Long qid, Long nqid) {
    try {
      QuestionModel res = questions.get(new GameDataCacheKey(party_id, qid));
      questions.get(new GameDataCacheKey(party_id, nqid));
      return res;
    } catch (Exception e) {
      return null;
    }
  }

  private Long getNextQuestionId(String party_id) {
    return 0L;
  }

  @Override
  public void store(String party_id, GameSettingsModel settings) {
    String json = new Gson().toJson(settings);
    redis.saveHash(party_id + ":s:e:t", new RedisObject(
        json,
        GameConfig.DEFAULT_TTL
    ));
  }

  @Override
  public void contains(String game_id, String creator_id) {

  }
}
