package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.ChoiceDictionary;
import com.example.game.datacontainer.ScoreDictionary;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import com.example.game.strategies.GradingStrategy;

import java.util.HashMap;

public class QStatisticsStateVisitor extends Visitor{
    public void calculateScore(String party_id, Question question, GradingStrategy strategy, ChoiceDictionary choices, ScoreDictionary scores) {
        
        HashMap<String, Score> playerScoreList = strategy.calculateScore(
                question,
                choices.getChoices(
                        party_id,
                        question.getQid().getQid()
                ),
                scores.getScore(party_id));
        scores.setScore(party_id, playerScoreList);
    }

    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.QStatisticsStateEvent.SEND_RESULT -> {
                System.out.println("At QStatisticsState, send result event occurred.");

                ChoiceDictionary choices = (ChoiceDictionary) params.get("choices");
                ScoreDictionary scores = (ScoreDictionary) params.get("scores");

                calculateScore(
                        (String) params.get(GameConfig.ParamName.PLAYER_ID),
                        (Question) params.get(GameConfig.ParamName.QUESTION),
                        (GradingStrategy) params.get(GameConfig.ParamName.GRADING_STRATEGY),
                        choices,
                        scores
                );
                // TO DO
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
