package com.example.game.strategies;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Question;
import com.example.game.entities.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class GradingTimeStrategy implements GradingStrategy {
    Long calculateBonusPoint(Long time, Long totalTime) {
        return points - (time * points / totalTime);
    }

    @Override
    public HashMap<String, Score> calculateScore(Question question, ArrayList<GameChoice> playerAnswerList, HashMap<String, Score> playerScoreList) {
        for (GameChoice playerAnswer: playerAnswerList) {
            Score playerScore = playerScoreList.get(playerAnswer.getPlayer_id());
            if (playerAnswer.getAid().equals(question.getCorrectAnswer())) {
                playerScore.setScore(playerScore.getScore() + calculateBonusPoint(playerAnswer.getTime(), question.getTime()));
            }
        }
        return playerScoreList;
    }
}
