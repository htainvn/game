package com.example.game.services;

import com.example.game.constant.GradingStrategyType;
import com.example.game.datacontainer.ChoiceDictionary;
import com.example.game.datacontainer.PlayerDictionary;
import com.example.game.datacontainer.ScoreDictionary;
import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import com.example.game.strategies.GradingDifficultyStrategy;
import com.example.game.strategies.GradingEqualStrategy;
import com.example.game.strategies.GradingStrategy;
import com.example.game.strategies.GradingTimeStrategy;
import com.example.game.strategies.ScoringEqual;
import com.example.game.strategies.ScoringTime;
import com.example.game.strategies.ScoringStrategy;
import jakarta.websocket.Session;

import java.util.HashMap;
import java.util.List;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GActionService implements GActionInterface {

  private PlayerDictionary players;
  private ChoiceDictionary choices;
  private ScoreDictionary scores;

  @Autowired
  public GActionService(PlayerDictionary players, ChoiceDictionary choices, ScoreDictionary scores) {
    this.players = players;
    this.choices = choices;
    this.scores = scores;
  }

  @Override
  public void receiveChoice(GameChoice choice) {
    choices.addChoice(choice);
  }

  @Override
  public List<Boolean> publishResults(String party_id) {
    return null;
  }

  @Override
  public Score getScore(String party_id, String player_id) {
    return scores.getScore(party_id, player_id);
  }

  @Override
  public void calculateScore(String party_id, Question question, String strategy) {
    GradingStrategy scoringStrategy;
    strategy = strategy.toLowerCase();

    switch (strategy) {
      case GradingStrategyType.TIME:
        scoringStrategy = new GradingTimeStrategy();
        break;
      case GradingStrategyType.EQUAL:
        scoringStrategy = new GradingEqualStrategy();
        break;
      case GradingStrategyType.DIFFICULTY:
        scoringStrategy = new GradingDifficultyStrategy();
        break;
      default:
        throw new ExecutionException("Invalid strategy");
    }

    HashMap<String, Score> playerScoreList = scoringStrategy.calculateScore(question, choices.getChoices(party_id, question.getId().getQid()), scores.getScore(party_id));
    scores.setScore(party_id, playerScoreList);
  }

  @Override
  public HashMap<String, Score> rank(String party_id) {
    return scores.getRanking(party_id);
  }

  @Override
  public void registerPlayer(Session session, Player player) {
    players.registerPlayer(session, player);
  }
}
