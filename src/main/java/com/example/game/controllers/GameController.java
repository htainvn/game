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
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/game")
public class GameController {
  private final GInfoService gInfoService;

  private final GActionService gActionService;

  @Autowired
  public GameController(GInfoService gInfoService, GActionService gActionService) {
    this.gInfoService = gInfoService;
    this.gActionService = gActionService;
  }

  @OnOpen
  public void onOpen(Session session, HttpServletRequest request) {
    System.out.println("WebSocket opened: " + request.getRemoteAddr());
    gActionService.registerPlayer(session, Player.builder().build());
  }

  @OnClose
  public void onClose(Session session) {
    System.out.println("WebSocket closed");
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    System.out.println("Message received: " + message);
  }

  @OnError
  public void onError(Throwable error) {
    System.out.println("Error: " + error.getMessage());
  }
}
