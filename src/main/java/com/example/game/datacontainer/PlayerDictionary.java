package com.example.game.datacontainer;

import com.example.game.entities.Player;
import jakarta.websocket.Session;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

@Component
public class PlayerDictionary {
  private HashMap<Session, Player> players;

  public Player getPlayer(Session session) {
    return players.get(session);
  }

  public void registerPlayer(Session session, Player player) {
    if (players == null) {
      players = new HashMap<Session, Player>();
    }
    if (!players.containsKey(session)) {
      players.put(session, player);
    }
  }

  public void removePlayer(Session session) {
    players.remove(session);
  }

  public boolean containsPlayer(Session session) {
    return players.containsKey(session);
  }

  public ArrayList<Pair<Session, Player>> getPlayerInAGame(String pid) {
    ArrayList<Pair<Session, Player>> playersInAGame = new ArrayList<Pair<Session, Player>>();
    for (Player player : players.values()) {
      if (player.getPid().equals(pid)) {
        playersInAGame.add(Pair.with(player.getSession(), player));
      }
    }
    return playersInAGame;
  }
}
