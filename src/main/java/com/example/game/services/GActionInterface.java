package com.example.game.services;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GActionInterface {
  public void receiveChoice(GameChoice choice);

  public List<Boolean> publishResults(String party_id);

  public Score getScore(String party_id, String player_id);

  void calculateScore(String party_id, Question question, String strategy);

  public HashMap<String, Score> rank(String pid);

  public void registerPlayer(Session session, Player player);
}
