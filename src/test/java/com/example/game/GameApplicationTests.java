package com.example.game;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.GameFlowType;
import com.example.game.config.GameConfig.GradingStrategyType;
import com.example.game.constant.ReqType;
import com.example.game.controllers.GameController;
import com.example.game.datacontainer.implementations.ChoiceDictionary;
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
import com.example.game.request.AnsweringPayload;
import com.example.game.request.GInitializeRequest;
import com.example.game.request.RegisterPayload;
import com.example.game.request.RequestData;
import com.example.game.response.GameInitializedResponse;
import com.example.game.response.GameRegisteredResponse;
import com.example.game.response.GameStartResponse;
import com.example.game.response.QEndResponse;
import com.example.game.response.QShowingResponse;
import com.example.game.security.StompPrincipal;
import com.example.game.services.GameService;
import com.example.game.state.LobbyState;
import com.example.game.state.QAnsweringState;
import com.example.game.state.QStatisticsState;
import com.example.game.strategies.GradingStrategy;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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

	@Autowired
	GameService gameService;

	@Autowired
	ChoiceDictionary choiceDictionary;

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
		System.out.println(response.getGameID());
		assert gameid != null;
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
		assert result.getPlayer_id() != null;
		System.out.println(gson.toJson(result));
	}

	String contextRegister(String gameID) {
		RequestData requestData = new RequestData();
		requestData.setGameID(gameID);
		requestData.setRequestType(ReqType.REGISTER);
		Gson gson = new Gson();
		RegisterPayload payload = new RegisterPayload();
		payload.setName(UUID.randomUUID().toString());
		requestData.setPayload(gson.toJson(payload));
		GameRegisteredResponse result = controller.registerPlayer(requestData, new StompPrincipal(UUID.randomUUID().toString()));
		return result.getPlayer_id();
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
		GameInitializedResponse response = controller.initializeGame(
				new GInitializeRequest(
						"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg",
						"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
						"3235b99d-863e-4566-96ad-f042e977fff5",
						GameFlowType.TIME_UP,
						GradingStrategyType.TIME
				)
		);
		String gameidx = response.getGameID();
		System.out.println(gameidx);
		GameStartResponse result = controller.startGame(
				new RequestData(
						gameidx,
						ReqType.START_GAME,
						null
				)
		);
		System.out.println(gson.toJson(result));
		QuestionModel question = gameDataDictionary.get(
				gameidx,
				0L,
				1L
		);
		System.out.println(gson.toJson(question));
		assert question != null;
  }

	@Test
	@Transactional
	void testShowingAnswer() throws InterruptedException {
		GameInitializedResponse response1 = controller.initializeGame(
				new GInitializeRequest(
						"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg",
						"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
						"3235b99d-863e-4566-96ad-f042e977fff5",
						GameFlowType.TIME_UP,
						GradingStrategyType.TIME
				)
		);
		String gameidx = response1.getGameID();
		System.out.println(gameidx);
		GameStartResponse result = controller.startGame(
				new RequestData(
						gameidx,
						ReqType.START_GAME,
						null
				)
		);
		QuestionModel question = gameDataDictionary.get(
				gameidx,
				0L,
				1L
		);
		System.out.println(gson.toJson(question));
		QShowingResponse response2 = controller.showGame(
				new RequestData(
						gameidx,
						ReqType.SHOW_QUESTION,
						null
				)
		);
		System.out.println(gson.toJson(response2));
		assert response2.getData() != null;
		Thread.sleep(10000);
		System.out.println(gameService.getGame(gameidx).getState().getClass());
		assert gameService.getGame(gameidx).getState().getClass() == QAnsweringState.class;
		Thread.sleep(60000);
		assert gameService.getGame(gameidx).getState().getClass() == QStatisticsState.class;
	}

	@Test
	void testAnswering() throws InterruptedException {
		GameInitializedResponse response1 = controller.initializeGame(
				new GInitializeRequest(
						"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg",
						"f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b",
						"3235b99d-863e-4566-96ad-f042e977fff5",
						GameFlowType.TIME_UP,
						GradingStrategyType.TIME
				)
		);
		String gameidx = response1.getGameID();
		System.out.println(gameidx);
		String user1 = contextRegister(gameidx);
		String user2 = contextRegister(gameidx);
		String user3 = contextRegister(gameidx);
		String user4 = contextRegister(gameidx);
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user3);
		System.out.println(user4);
		assert !Objects.equals(user1, user2);
		assert !Objects.equals(user1, user3);
		assert !Objects.equals(user1, user4);
		assert !Objects.equals(user2, user3);
		assert !Objects.equals(user2, user4);
		assert !Objects.equals(user3, user4);
		GameStartResponse result = controller.startGame(
				new RequestData(
						gameidx,
						ReqType.START_GAME,
						null
				)
		);
		QuestionModel question = gameDataDictionary.get(
				gameidx,
				0L,
				1L
		);
		System.out.println(gson.toJson(question));
		QShowingResponse response2 = controller.showGame(
				new RequestData(
						gameidx,
						ReqType.SHOW_QUESTION,
						null
				)
		);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		executor.execute(() -> {
			try {
				Thread.sleep(6000);
				Thread.sleep(1000);
				controller.answerGame(
						new RequestData(
								gameidx,
								ReqType.SEND_CHOICE,
								gson.toJson(new AnsweringPayload(
									user1,
									1L,
									1L
								))
						)
				);
				Thread.sleep(1000);
				controller.answerGame(
						new RequestData(
								gameidx,
								ReqType.SEND_CHOICE,
								gson.toJson(new AnsweringPayload(
									user2,
									1L,
									2L
								))
						)
				);
				Thread.sleep(1000);
				controller.answerGame(
						new RequestData(
								gameidx,
								ReqType.SEND_CHOICE,
								gson.toJson(new AnsweringPayload(
										user3,
										1L,
										3L
								))
						)
				);
				Thread.sleep(1000);
				controller.answerGame(
						new RequestData(
								gameidx,
								ReqType.SEND_CHOICE,
								gson.toJson(new AnsweringPayload(
										user4,
										1L,
										2L
								))
						)
				);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		Thread.sleep(60000);
		QEndResponse response = controller.getStats(
				new RequestData(
						gameidx,
						ReqType.GET_STATS,
						null
				)
		);
		System.out.println(gson.toJson(response));
		assert response.getStatistics() != null;
 	}
}
