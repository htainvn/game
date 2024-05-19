//package com.example.game.config;
//
//import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
//import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
//
//@Configuration
//public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
////  @Override
////  protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
////    messages
////        // users cannot send to these broker destinations, only the application can
////        .simpMessageDestMatchers("/topic/chat.login", "/topic/chat.logout", "/topic/chat.message").denyAll()
////        .anyMessage().authenticated();
////  }
////
////  @Override
////  protected boolean sameOriginDisabled() {
////    //disable CSRF for websockets for now...
////    return true;
////  }
//}