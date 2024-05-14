package com.example.game.datacontainer;

import com.example.game.dto.GameChoice;

import java.util.ArrayList;

public interface IChoiceDictionary {
    public GameChoice get(
            String party_id,
            String qid,
            String player_id
    );

    public void addChoice(GameChoice choice);

    public ArrayList<GameChoice> getChoices(String party_id, String qid);

    public void removeAllChoicesFromParty(String pid);
}
