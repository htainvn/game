package com.example.game.datacontainer;

import com.example.game.dto.GameChoice;

import java.util.ArrayList;

public class TempChoiceDictionary implements IChoiceDictionary{
    @Override
    public GameChoice get(String party_id, String qid, String player_id) {
        return null;
    }

    @Override
    public void addChoice(GameChoice choice) {

    }

    @Override
    public ArrayList<GameChoice> getChoices(String party_id, String qid) {
        return null;
    }

    @Override
    public void removeAllChoicesFromParty(String pid) {

    }
}
