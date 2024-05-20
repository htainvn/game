package com.example.game.datacontainer.interfaces;

import com.example.game.entities.Score;

import java.util.HashMap;

public interface IScoreDictionary {
    public Score getScore(String party_id, String player_id, Long iteration);
    public HashMap<String, Score> getScore(String party_id, Long iteration);

    public HashMap<String, Score> getScoreInParty(String party_id);

    public void setScore(String party_id, HashMap<String, Score> score);

    public HashMap<String, Score> getRanking(String party_id);

    public void refreshRanking(String party_id);

    public void createScore(String party_id, String player_id, Long iteration);
}
