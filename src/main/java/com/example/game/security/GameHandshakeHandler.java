package com.example.game.security;

import com.example.game.security.StompPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


public class GameHandshakeHandler extends DefaultHandshakeHandler {
  @Override
  protected Principal determineUser(
      @NotNull ServerHttpRequest request,
      @NotNull WebSocketHandler wsHandler,
      @NotNull Map<String, Object> attributes
  ) {
    // Generate principal with UUID as name
    return new StompPrincipal(UUID.randomUUID().toString());
  }
}
