package com.example.game.strategies;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Question;
import com.example.game.entities.Score;

import java.util.ArrayList;
import java.util.HashMap;

public interface GradingStrategy {
    Long points = 1000L;
    HashMap<String, Score> calculateScore(Question question, ArrayList<GameChoice> playerAnswerList, HashMap<String, Score> playerScoreList);
}
