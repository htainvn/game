package com.example.game.controllers;

import com.example.game.config.GameConfig;
import com.example.game.constant.ReqType;
import com.example.game.entities.Question;
import com.example.game.request.AnsweringPayload;
import com.example.game.request.GInitializeRequest;
import com.example.game.request.RegisterPayload;
import com.example.game.request.RequestData;
import com.example.game.response.*;
import com.example.game.services.GameService;
import com.google.gson.Gson;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;

@Component
@Controller
@RequestMapping("/game")
public class GameController {
  @Autowired
  private GameService gameService;

  @MessageMapping("/initialize")
  @SendToUser("/topic/game.INITIALIZED")
  public GameInitializedResponse initializeGame(@Payload GInitializeRequest request) {
    Pair<String, String> gameInf = gameService.createGame(
        request.getQuizID(),
        request.getAuthID(),
        request.getGameFlowMode(),
        request.getGradingStrategy()
    );

    return new GameInitializedResponse(gameInf.getValue0(), gameInf.getValue1());
  }
  @MessageMapping("/register")
  @SendToUser("/topic/game.REGISTERED")
  public GameRegisteredResponse registerPlayer(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.REGISTER)) {
      throw new UnsupportedOperationException();
    }
    Gson payloadGson = new Gson();
    RegisterPayload payload = payloadGson.fromJson(request.getPayload(), RegisterPayload.class);
    Pair<String, String> playerInf = gameService.registerPlayerToGame(
        request.getGameID(),
        payload.getName()
    );

    return new GameRegisteredResponse(playerInf.getValue0(), playerInf.getValue1());
  }

  @MessageMapping("/start")
  @SendTo("/topic/game.RUNNING")
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
    Question question = (Question) result.get(GameConfig.ParamName.QUESTION);
    QShowingResponse response = new QShowingResponse(request.getGameID(), result.get(GameConfig.ParamName.CURRENT_QUESTION_CNT).toString(), question.getCategory(), question.getStatement(), question.getAnswers(), question.getTime(), result.get(GameConfig.ParamName.QUESTION_TIME_OUT).toString());
  }

  @MessageMapping("/answer")
  @SendToUser("/topic/game.ANSWERED")
  public QAnswerResponse answerGame(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.SEND_CHOICE)) {
      throw new UnsupportedOperationException();
    }

    AnsweringPayload payload = new Gson().fromJson(request.getPayload(), AnsweringPayload.class);
    gameService.receiveAnswer(
        request.getGameID(), payload.getPlayerID(), payload.getAnswerID());

    return new QAnswerResponse();
  }

  @MessageMapping("/stats")
  @SendToUser("/topic/game.STATS")
  public QEndResponse getStats(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.GET_STATS)) {
      throw new UnsupportedOperationException();
    }

    gameService.getResults(request.getGameID());
    return new QEndResponse();
  }

  @MessageMapping("/rank")
  @SendToUser("/topic/game.RANK")
  public GameRankingResponse getRank(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.GET_RANK)) {
      throw new UnsupportedOperationException();
    }
    gameService.getRanking(request.getGameID());
    return new GameRankingResponse();
  }

  @MessageMapping("/end")
  @SendTo("/topic/game.ENDED")
  public GameFinalRankingResponse endGame(@Payload RequestData request) {
    if (!Objects.equals(request.getRequestType(), ReqType.END_GAME)) {
      throw new UnsupportedOperationException();
    }
    gameService.endGame(request.getGameID());
    return new GameFinalRankingResponse();
  }


}
