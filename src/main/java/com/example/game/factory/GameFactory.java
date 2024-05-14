package com.example.game.factory;

import com.example.game.config.GameConfig;
import com.example.game.executor.GameExecutor;
import com.example.game.executor.MaxCorrectGameExecutor;
import com.example.game.executor.TimeUpGameExecutor;
import com.example.game.state.EmptyGameState;
import com.example.game.state.LobbyState;
import com.example.game.strategies.GradingDifficultyStrategy;
import com.example.game.strategies.GradingEqualStrategy;
import com.example.game.strategies.GradingTimeStrategy;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

public class GameFactory {
  public GameFactory() {

  }

  /* Factory + Builder */
  public static GameExecutor createGame(String gameFlowMode, String gradingStrategy) {
    GameExecutor product;
    if (Objects.equals(gameFlowMode, GameConfig.GameFlowType.MAX_CORRECT)) {
      product = new MaxCorrectGameExecutor();
    } else if (Objects.equals(gameFlowMode, GameConfig.GameFlowType.TIME_UP)) {
      product = new TimeUpGameExecutor();
    } else {
      throw new UnsupportedOperationException();
    }
    product.setGameID(UUID.randomUUID().toString());
    product.setState(new EmptyGameState());
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