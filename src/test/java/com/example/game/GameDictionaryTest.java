package com.example.game;

import com.example.game.config.GameConfig.GameFlowType;
import com.example.game.config.GameConfig.GradingStrategyType;
import com.example.game.controllers.GameController;
import com.example.game.datacontainer.implementations.GameDataDictionary;
import com.example.game.datacontainer.implementations.PlayerDictionary;
import com.example.game.model.GameSettingsModel;
import com.example.game.redis.RedisRepo;
import com.example.game.redis.RedisService;
import com.example.game.request.GInitializeRequest;
import com.example.game.response.GameInitializedResponse;
import com.example.game.services.DataService;
import com.google.gson.Gson;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.user.SimpUserRegistry;

@SpringBootTest
public class GameDictionaryTest extends GameDataDictionary {

  @Autowired
  RedisService redisService;

  @Autowired
  GameController controller;

  @Autowired
  PlayerDictionary playerDictionary;

  @Autowired
  SimpUserRegistry simpUserRegistry;

  @Autowired
  GameDataDictionary gameDataDictionary;

  private final Gson gson = new Gson();

  private String gameid;

  @Autowired
  public GameDictionaryTest(
      DataService dataService,
      RedisRepo redis
  ) {
    super(dataService, redis);
  }

  @Test
  void contextLoads() {
    GameInitializedResponse response = controller.initializeGame(
        new GInitializeRequest(
            "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg",
            "f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
            "3235b99d-863e-4566-96ad-f042e977fff5",
            GameFlowType.TIME_UP,
            GradingStrategyType.TIME
        )
    );
    gameid = response.getGameID();
  }

  @Test
  void getSettings() throws ExecutionException {
    contextLoads();
    GameSettingsModel settingsModel = settings.get(gameid);
    System.out.println(gson.toJson(settingsModel));
  }

  @Test
  void testGson() {
    GameSettingsModel settings = gson.fromJson(
        gson.toJson(new GameSettingsModel(
            "party_id",
            "gameFlowMode",
            "gradingStrategy",
            true,
            true)
        ),
        GameSettingsModel.class
    );
  }
}
