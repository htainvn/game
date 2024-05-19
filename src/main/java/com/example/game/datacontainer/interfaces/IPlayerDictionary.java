package com.example.game.datacontainer.interfaces;

import com.example.game.entities.Player;
import java.util.ArrayList;
import java.util.List;

public interface IPlayerDictionary {
    public void registerPlayer(String party_id, Player player);
    public boolean containsPlayer(String party_id, String name);
    public ArrayList<Player> getAllPlayers(String party_id);
}
