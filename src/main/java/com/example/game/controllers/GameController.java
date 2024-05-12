package com.example.game.controllers;

import com.example.game.entities.Player;
import com.example.game.request.GInitializeRequest;
import com.example.game.request.RegisterPayload;
import com.example.game.request.RequestData;
import com.example.game.response.GameInitializedResponse;
import com.example.game.services.GActionService;
import com.example.game.services.GInfoService;
import com.example.game.services.GameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

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
  public GameInitializedResponse registerPlayer(@Payload RequestData request) {

  }

  @MessageMapping("/start")
  @SendTo("/topic/game.RUNNING")
  public GameInitializedResponse startGame(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }

  @MessageMapping("/show")
  @SendTo("/topic/game.SHOW")
  public GameInitializedResponse showGame(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }

  @MessageMapping("/answer")
  @SendToUser("/topic/game.ANSWERED")
  public GameInitializedResponse answerGame(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }

  @MessageMapping("/stats")
  @SendToUser("/topic/game.STATS")
  public GameInitializedResponse getStats(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }

  @MessageMapping("/rank")
  @SendToUser("/topic/game.RANK")
  public GameInitializedResponse getRank(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }

  @MessageMapping("/end")
  @SendTo("/topic/game.ENDED")
  public GameInitializedResponse endGame(@Payload RequestData request) {
    throw new UnsupportedOperationException();
  }


}
