package com.example.game.services;

import com.example.game.datacontainer.ChoiceDictionary;
import com.example.game.datacontainer.PlayerDictionary;
import com.example.game.datacontainer.ScoreDictionary;
import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.entities.Score;
import jakarta.websocket.Session;
import java.util.HashMap;
import java.util.List;
import org.hibernate.sql.exec.ExecutionException;
import org.javatuples.Pair;
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

  }

  @Override
  public List<Boolean> publishResults(String pid) {
    return null;
  }

  @Override
  public Score calculateScore(String pid, String player_id) {
    throw new ExecutionException("Not implemented");
  }

  @Override
  public HashMap<String, Score> rank(String pid) {
    return scores.getRanking(pid);
  }

  @Override
  public void registerPlayer(Session session, Player player) {
    players.registerPlayer(session, player);
  }
}
