package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.TempChoiceDictionary;
import com.example.game.datacontainer.TempScoreDictionary;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import com.example.game.strategies.GradingStrategy;

import java.util.HashMap;

public class QStatisticsStateVisitor extends Visitor{
    public void calculateScore(
            String party_id,
            Question question,
            GradingStrategy strategy,
            TempChoiceDictionary choices,
            TempScoreDictionary scores
    ) {
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

                TempChoiceDictionary choices = (TempChoiceDictionary) params.get(GameConfig.ParamName.CHOICE_DICTIONARY);
                TempScoreDictionary scores = (TempScoreDictionary) params.get(GameConfig.ParamName.SCORE_DICTIONARY);

                calculateScore(
                        gameExecutor.getGameID(),
                        (Question) params.get(GameConfig.ParamName.QUESTION),
                        (GradingStrategy) params.get(GameConfig.ParamName.GRADING_STRATEGY),
                        choices,
                        scores
                );
                // TO DO
                HashMap<String, Object> result = new HashMap<>();
                result.put(GameConfig.ParamName.CHOICE_DICTIONARY, scores.getScore(
                        gameExecutor.getGameID()
                ));
                return result;
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
