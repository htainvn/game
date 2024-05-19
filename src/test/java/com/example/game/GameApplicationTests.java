package com.example.game;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.GameFlowType;
import com.example.game.config.GameConfig.GradingStrategyType;
import com.example.game.constant.ReqType;
import com.example.game.controllers.GameController;
import com.example.game.datacontainer.implementations.GameDataDictionary;
import com.example.game.datacontainer.implementations.PlayerDictionary;
import com.example.game.dto.OriginalQuizDto;
import com.example.game.dto.RequestMessage;
import com.example.game.entities.GameQuizDto;
import com.example.game.entities.Player;
import com.example.game.helper.RandomCode;
import com.example.game.model.GameSettingsModel;
import com.example.game.model.QuestionModel;
import com.example.game.redis.RedisService;
import com.example.game.request.GInitializeRequest;
import com.example.game.request.RegisterPayload;
import com.example.game.request.RequestData;
import com.example.game.response.GameInitializedResponse;
import com.example.game.response.GameRegisteredResponse;
import com.example.game.response.GameStartResponse;
import com.example.game.security.StompPrincipal;
import com.example.game.state.LobbyState;
import com.example.game.strategies.GradingStrategy;
import com.google.gson.Gson;
import java.util.Objects;
import java.util.UUID;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.userdetails.User;

@SpringBootTest
class GameApplicationTests {

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

	Gson gson = new Gson();

	String gameid;

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
		//testRegister(response.getGameID());
	}

	@Test
	void dump() {
		System.out.println(gson.toJson(new GInitializeRequest(
				"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg",
				"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
				"3235b99d-863e-4566-96ad-f042e977fff5",
				GameFlowType.TIME_UP,
				GradingStrategyType.TIME
		)));
		System.out.println(gson.toJson(new RequestData(
				"174dfd18-a75c-483d-a9e3-b89facd6a409",
				ReqType.REGISTER,
				gson.toJson(new RegisterPayload("htainvn"))
		)));
	}

	@Test
	void testRegister() {
		contextLoads();
		String gameID = gameid;
		RequestData requestData = new RequestData();
		requestData.setGameID(gameID);
		requestData.setRequestType(ReqType.REGISTER);
		Gson gson = new Gson();
		RegisterPayload payload = new RegisterPayload();
		payload.setName("htainvn");
		requestData.setPayload(gson.toJson(payload));
		GameRegisteredResponse result = controller.registerPlayer(requestData, new StompPrincipal(UUID.randomUUID().toString()));
		assert result.getRegisterStatus().equals("success");
		System.out.println(gson.toJson(result));
	}

	@Test
	void testPlayerDictionary() {
		playerDictionary.registerPlayer(
				"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
				new Player("f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b", "htainvn")
		);
		playerDictionary.getAllPlayers("f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b").forEach(
				player -> {
					System.out.println(player.getName());
				}
		);
		assert playerDictionary.containsPlayer(
				"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
				"htainvn"
		);
	}

	@Test
	void testStartGame() {
		contextLoads();
		GameStartResponse result = controller.startGame(
				new RequestData(
						gameid,
						ReqType.START_GAME,
						null
				)
		);
		System.out.println(gson.toJson(result));
		QuestionModel question = gameDataDictionary.get(
				gameid,
				0L,
				1L
		);
		System.out.println(gson.toJson(question));
		assert question != null;
  }
}
