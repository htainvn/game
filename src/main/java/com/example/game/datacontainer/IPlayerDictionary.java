package com.example.game.datacontainer;

import com.example.game.entities.Player;

public interface IPlayerDictionary {
    public void registerPlayer(String party_id, Player player);
    public boolean containsPlayer(String party_id, String name);
}
