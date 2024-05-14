package com.example.game.datacontainer;

import com.example.game.entities.Score;

import java.util.HashMap;

public class TempScoreDictionary implements IScoreDictionary{
    @Override
    public Score getScore(String party_id, String player_id) {
        return null;
    }

    @Override
    public HashMap<String, Score> getScore(String party_id) {
        return null;
    }

    @Override
    public HashMap<String, Score> getScoreInParty(String party_id) {
        return null;
    }

    @Override
    public void setScore(String party_id, HashMap<String, Score> score) {

    }

    @Override
    public HashMap<String, Score> getRanking(String party_id) {
        return null;
    }
}
