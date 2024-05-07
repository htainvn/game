package com.example.game.controllers;

import com.example.game.entities.Player;
import com.example.game.services.GActionService;
import com.example.game.services.GInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class GameController {


  private GInfoService gInfoService;

  private GActionService gActionService;

  @Autowired
  public GameController(GInfoService gInfoService, GActionService gActionService) {
      this.gInfoService = gInfoService;
      this.gActionService = gActionService;
  }

  @MessageMapping("/game.HELLO")
  @SendTo("/topic/game.HELLO")
  public String hello() {
    return "Hello, World!";
  }

  @MessageMapping("/game.REG")
  @SendTo("/topic/game.REG")
  public Player register(@Payload Player player) {
      return player;
  }
}
