package com.example.game.controllers;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.DataServiceType;
import com.example.game.constant.ReqType;
import com.example.game.dto.OriginalQuizDto;
import com.example.game.entities.Game;
import com.example.game.entities.GameQuestionDto;
import com.example.game.entities.GameQuizDto;
import com.example.game.model.QuestionModel;
import com.example.game.repository.GameRepository;
import com.example.game.request.AnsweringPayload;
import com.example.game.request.GInitializeRequest;
import com.example.game.request.RegisterPayload;
import com.example.game.request.RequestData;
import com.example.game.response.*;
import com.example.game.services.DataService;
import com.example.game.services.GameService;
import com.example.game.services.RestService;
import com.google.gson.Gson;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;
import org.springframework.web.bind.annotation.RestController;

@Component
@Controller
@RestController
@RequestMapping("/game")
public class GameController {
  @Autowired
  private GameService gameService;
  private final SimpUserRegistry simpUserRegistry;
  private final SimpMessagingTemplate messagingTemplate;
  private final RestService restService;
  private final DataService dataService;
  private final GameRepository gameDataRepo;

  @MessageMapping("/test-initialized")
  @SendTo("/topic/general")
  public GameInitializedResponse testInitialized() {
    return new GameInitializedResponse("gameID", "gameAccessCode");
  }

  @MessageMapping("/test-register")
  @SendToUser("/queue/game.REGISTERED")
  public GameRegisteredResponse testRegister() {
    return new GameRegisteredResponse("playerID", "playerAccessCode");
  }

  @MessageMapping("/test-start")
  @SendToUser("/queue/game.START")
  public GameStartResponse testStart() {
    return new GameStartResponse("gameID");
  }

  @MessageMapping("/test-show")
  @SendTo("/topic/game.SHOW")
  public QShowingResponse testShow() {
    return new QShowingResponse("gameID", 1L, "question", "answers", 10L, 10L);
  }

  @MessageMapping("/test-answer")
  @SendToUser("/topic/game.ANSWERED")
  public QAnswerResponse testAnswer() {
    return new QAnswerResponse();
  }

  @MessageMapping("/test-skip")
  @SendToUser("/topic/game.SKIPPED")
  public Response testSkip() {
    return new Response();
  }

  @MessageMapping("/test-stats")
  @SendToUser("/topic/game.STATS")
  public QEndResponse testStats() {
    return new QEndResponse("gameID", "choiceDictionary");
  }

  @MessageMapping("/test-rank")
  @SendToUser("/topic/game.RANK")
  public GameRankingResponse testRank() {
    return new GameRankingResponse("gameID", "scoreDictionary");
  }

  @MessageMapping("/test-end")
  @SendTo("/topic/game.ENDED")
  public GameFinalRankingResponse testEnd() {
    return new GameFinalRankingResponse("gameID", "scoreDictionary");
  }

  public GameController(
      SimpUserRegistry simpUserRegistry,
      SimpMessagingTemplate messagingTemplate,
      RestService restService,
      GameRepository gameDataRepo, DataService dataService, GameRepository gameDataRepo1) {
    this.simpUserRegistry = simpUserRegistry;
    this.messagingTemplate = messagingTemplate;
    this.restService = restService;
    this.dataService = dataService;
    this.gameDataRepo = gameDataRepo1;
  }

  @MessageMapping("/ping")
  @SendTo("/topic/game.pong")
  public String ping() {
    System.out.println("Ping");
    return "pong";
  }

  @GetMapping("/test")
  public List<GameQuizDto> test() {
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMjM1Yjk5ZC04NjNlLTQ1NjYtOTZhZC1mMDQyZTk3N2ZmZjUiLCJlbWFpbCI6ImJsYW5kaHVuZy5kZXZAZ21haWwuY29tIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MTU5MzM0NDI2MTAsImV4cCI6MTcxNjUzODI0MjYxMH0.WRbhSx0ndWSD_YS0rbfb3paGPRGbddhHYK4LS7pVwxOqjaoMX04FOP-rxN_xIIBGA34sHvBf9M1oz0H7iwP5NizmbQmpoK-fiTnhhhWvIXBuEBgaVzPivqSggZzvfk6lACoB51rv6B5_sUr6ssd8yhecz2XftYnPR1Zl4dM29IY2G_uhdFiMXuNjul1VX7ng9GVEBsypky-ztYF9zEgkil2ewIPgRR5KcauqklHNrlNWu1VKmxNDabGvt_A8IxPd3fd-Or7TCJUX6n9-t9vz2v_5861x840BUReKGXwRiwHJw7189Uz7msgz6Wa2Y_zUlZxKNBLq99-MeGKwObMJWg");
    String objStr = restService.getWithHeaders("http://localhost:4000/quiz/f1b3b3b1-0b3b-4b3b-8b3b-0b3b3b3b3b3b", "", headers);
    Gson gson = new Gson();
    OriginalQuizDto originalQuizDto = gson.fromJson(objStr, OriginalQuizDto.class);
    Game game = new Game(UUID.randomUUID().toString(), originalQuizDto);
//    System.out.println(game);
//    return gameDataRepo.findAll();
    dataService.store(DataServiceType.PERSIST_GAME, new HashMap<>() {{
      put(GameConfig.DataParamName.GAME, game);
    }});
    return gameDataRepo.findAll().stream().map(GameQuizDto::new).toList();
  }

  @MessageMapping("/")
  public Response test(
      Principal principal
  ) {
    System.out.println("Connected");
    Collection<SimpUser> users = simpUserRegistry.getUsers();
    for (SimpUser user : users) {
      if (user.getName().equals(principal.getName())) {
        System.out.println("User: " + user.getName());
      }
      messagingTemplate.convertAndSendToUser(
          user.getName(),
          "/queue/game",
          new Response()
      );
    }
    return new Response();
  }

  @MessageMapping("/initialize")
  @SendToUser("/queue/game.INITIALIZED")
  public GameInitializedResponse initializeGame(
      @Payload GInitializeRequest request
  ) {
    System.out.println("Receive Initialized Request");
    System.out.println(new Gson().toJson(request));
    Pair<String, String> gameInf = gameService.createGame(
        request.getQuizID(),
        request.getAuthID(),
        request.getGameFlowMode(),
        request.getGradingStrategy(),
        request.getAuthHeader()
    );
    return new GameInitializedResponse(gameInf.getValue0(), gameInf.getValue1());
  }

  @MessageMapping("/register")
  @SendToUser("/queue/game.REGISTERED")
  public GameRegisteredResponse registerPlayer(@Payload RequestData request, Principal principal) {
    if (!Objects.equals(request.getRequestType(), ReqType.REGISTER)) {
      throw new UnsupportedOperationException();
    }
    Gson payloadGson = new Gson();
    RegisterPayload payload = payloadGson.fromJson(request.getPayload(), RegisterPayload.class);
    Pair<String, String> playerInf = gameService.registerPlayerToGame(
        request.getGameID(),
        payload.getName(),
        principal.getName()
    );
    return new GameRegisteredResponse(playerInf.getValue0(), playerInf.getValue1());
  }

  @MessageMapping("/start")
  @SendToUser("/queue/game.START")
  public GameStartResponse startGame(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.START_GAME)) {
      throw new UnsupportedOperationException();
    }
    gameService.startGame(request.getGameID());
    return new GameStartResponse(request.getGameID());
  }

  @MessageMapping("/show")
  @SendTo("/topic/game.SHOW")
  public QShowingResponse showGame(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.SHOW_QUESTION)) {
      throw new UnsupportedOperationException();
    }
    HashMap<String, Object> result = gameService.showQuestion(request.getGameID());
    QuestionModel question = (QuestionModel) result.get(GameConfig.ParamName.QUESTION);
    QShowingResponse response = new QShowingResponse(
            request.getGameID(),
            (Long) result.get(GameConfig.ParamName.CURRENT_QUESTION_CNT),
            question.getData(),
            question.getAnswers().toString(),
            question.getTime(),
            GameConfig.DEFAULT_SHOWING_TIME
    );
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    executor.execute(() -> {
      try {
        Thread.sleep(GameConfig.DEFAULT_SHOWING_TIME);
        gameService.timeOutShowingQuestion(request.getGameID());
        ThreadPoolExecutor second_executors = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        second_executors.execute(() -> {
          try {
            Thread.sleep(question.getTime());
            gameService.timeOutAnsweringQuestion(request.getGameID());
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });
      } catch (InterruptedException e) {
        System.out.println("Thread Showing interrupted");
      }
    });
    return response;
  }

  @MessageMapping("/answer")
  @SendToUser("/topic/game.ANSWERED")
  public QAnswerResponse answerGame(
      @Payload RequestData request
  ) {
    if (!Objects.equals(request.getRequestType(), ReqType.SEND_CHOICE)) {
      throw new UnsupportedOperationException();
    }
    AnsweringPayload payload = new Gson().fromJson(request.getPayload(), AnsweringPayload.class);
    System.out.print("At this stage 1, the name: ");
    System.out.println(payload.getPlayerID());
    gameService.receiveAnswer(
        request.getGameID(),
        payload.getPlayerID(),
        payload.getAnswerID()
    );
    return new QAnswerResponse();
  }

  @MessageMapping("/skip-question")
  @SendToUser("/topic/game.SKIPPED")
  public Response skipQuestion(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.SKIP_QUESTION)) {
      throw new UnsupportedOperationException();
    }

    gameService.skipQuestion(request.getGameID());
    return new Response();
  }

  @MessageMapping("/stats")
  @SendToUser("/topic/game.STATS")
  public QEndResponse getStats(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.GET_STATS)) {
      throw new UnsupportedOperationException();
    }

    HashMap<String, Object> result = gameService.getResults(request.getGameID());
    Gson gson = new Gson();
    QEndResponse response = new QEndResponse(
                              request.getGameID(),
                              gson.toJson(result.get(GameConfig.ParamName.CHOICE_DICTIONARY))
    );
    return response;
  }

  @MessageMapping("/rank")
  @SendToUser("/topic/game.RANK")
  public GameRankingResponse getRank(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.GET_RANK)) {
      throw new UnsupportedOperationException();
    }
    HashMap<String, Object> result = gameService.getRanking(request.getGameID());
    GameRankingResponse response =
            new GameRankingResponse(
                    request.getGameID(),
                    result.get(GameConfig.ParamName.SCORE_DICTIONARY).toString()
            );
    return response;
  }

  @MessageMapping("/end")
  @SendTo("/topic/game.ENDED")
  public GameFinalRankingResponse endGame(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.END_GAME)) {
      throw new UnsupportedOperationException();
    }
    HashMap<String, Object> result = gameService.endGame(request.getGameID());
    GameFinalRankingResponse response =
            new GameFinalRankingResponse(
                    request.getGameID(),
                    result.get(GameConfig.ParamName.SCORE_DICTIONARY).toString()
            );
    return response;
  }

}
