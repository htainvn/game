package com.example.game.datacontainer.interfaces;

import com.example.game.dto.GameChoice;

import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

public interface IChoiceDictionary {
    @Nullable
    public GameChoice get(
            String party_id,
            Long qid,
            String player_id
    );

    public void addChoice(GameChoice choice);

    @Nullable
    public ArrayList<GameChoice> getChoices(String party_id, Long qid);

    public void removeAllChoicesFromParty(String pid);
}
