package com.example.game.datacontainer;

import com.example.game.entities.Player;

public class TempPlayerDictionary implements IPlayerDictionary{
    @Override
    public void registerPlayer(String party_id, Player player) {

    }

    @Override
    public boolean containsPlayer(String party_id, String name) {
        return false;
    }
}
