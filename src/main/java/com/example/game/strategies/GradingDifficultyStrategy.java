package com.example.game.strategies;

import com.example.game.dto.GameChoice;
import com.example.game.entities.Question;
import com.example.game.entities.Score;

import com.example.game.model.QuestionModel;
import java.util.ArrayList;
import java.util.HashMap;

public class GradingDifficultyStrategy implements GradingStrategy{

    @Override
    public HashMap<String, Score> calculateScore(
        QuestionModel question,
        ArrayList<GameChoice> playerAnswerList,
        HashMap<String, Score> playerScoreList
    ) {
        long countCorrectAnswer = 0L;
        for (GameChoice playerAnswer: playerAnswerList) {
            if (playerAnswer.getAid().equals(question.getCorrectAnswer())) {
                countCorrectAnswer++;
            }
        }

        Long bonusScore = points / playerAnswerList.size() * countCorrectAnswer;
        for (GameChoice playerAnswer: playerAnswerList) {
            Score playerScore = playerScoreList.get(playerAnswer.getPlayer_id());
            if (playerAnswer.getAid().equals(question.getCorrectAnswer())) {
                playerScore.setScore(playerScore.getScore() + bonusScore);
            }
        }

        return playerScoreList;
    }
}
