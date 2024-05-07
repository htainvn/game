package com.example.game.services;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Player;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import jakarta.websocket.Session;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import org.javatuples.Pair;

public interface GActionInterface {
  public void receiveChoice(GameChoice choice);

  public List<Boolean> publishResults(String pid);

  public Score calculateScore(String pid, String player_id);

  public HashMap<String, Score> rank(String pid);

  public void registerPlayer(Session session, Player player);
}
