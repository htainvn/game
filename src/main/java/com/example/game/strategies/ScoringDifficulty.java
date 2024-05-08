package com.example.game.strategies;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Question;
import com.example.game.entities.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoringDifficulty implements ScoringStrategy{

    @Override
    public HashMap<String, Score> calculateScore(Question question, ArrayList<GameChoice> playerAnswerList, HashMap<String, Score> playerScoreList) {
        Long countCorrectAnswer = 0L;
        for (GameChoice playerAnswer: playerAnswerList) {
            if (playerAnswer.getAid().equals(question.getCorrectAnswer())) {
                countCorrectAnswer++;
            }
        }

        Long bonusScore = question.getPoints() / playerAnswerList.size() * countCorrectAnswer;
        for (GameChoice playerAnswer: playerAnswerList) {
            Score playerScore = playerScoreList.get(playerAnswer.getPlayer_id());
            if (playerAnswer.getAid().equals(question.getCorrectAnswer())) {
                playerScore.setScore(playerScore.getScore() + bonusScore);
            }
        }

        return playerScoreList;
    }
}
