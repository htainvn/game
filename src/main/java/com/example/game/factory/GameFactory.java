package com.example.game.factory;

import com.example.game.config.GameConfig;
import com.example.game.executor.GameExecutor;
import com.example.game.executor.MaxCorrectGameExecutor;
import com.example.game.executor.TimeUpGameExecutor;
import com.example.game.services.DataService;
import com.example.game.state.EmptyGameState;
import com.example.game.state.GameState;
import com.example.game.strategies.GradingDifficultyStrategy;
import com.example.game.strategies.GradingEqualStrategy;
import com.example.game.strategies.GradingTimeStrategy;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

  private DataService dataService;

  private SimpMessagingTemplate simpMessagingTemplate;

  private SimpUserRegistry registry;

  @Autowired
  public GameFactory(
      DataService dataService,
      SimpMessagingTemplate simpMessagingTemplate,
      SimpUserRegistry registry
  ) {
    this.dataService = dataService;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.registry = registry;
  }

  /* Factory + Builder */
  public GameExecutor createGame(String gameFlowMode, String gradingStrategy) throws UnsupportedOperationException {
    GameExecutor product;
    if (Objects.equals(gameFlowMode, GameConfig.GameFlowType.MAX_CORRECT)) {
      product = new MaxCorrectGameExecutor(
          dataService,
          registry
      );
    } else if (Objects.equals(gameFlowMode, GameConfig.GameFlowType.TIME_UP)) {
      product = new TimeUpGameExecutor(
          dataService,
          registry
      );
    } else {
      throw new UnsupportedOperationException();
    }
    product.setGameID(UUID.randomUUID().toString());
    GameState state = new EmptyGameState();
    product.setState(state);
    state.setGameExecutor(product);
    switch (gradingStrategy) {
      case GameConfig.GradingStrategyType.TIME:
        product.setStrategy(new GradingTimeStrategy());
        break;
      case GameConfig.GradingStrategyType.DIFFICULTY:
        product.setStrategy(new GradingDifficultyStrategy());
        break;
      case GameConfig.GradingStrategyType.EQUAL:
        product.setStrategy(new GradingEqualStrategy());
        break;
      default:
        throw new UnsupportedOperationException();
    }
    return product;
  }
}