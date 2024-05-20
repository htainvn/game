package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.implementations.ChoiceDictionary;
import com.example.game.datacontainer.implementations.ScoreDictionary;
import com.example.game.datacontainer.interfaces.IChoiceDictionary;
import com.example.game.datacontainer.interfaces.IScoreDictionary;
import com.example.game.entities.Question;
import com.example.game.entities.Score;
import com.example.game.model.QuestionModel;
import com.example.game.strategies.GradingStrategy;

import java.util.HashMap;

public class QStatisticsStateVisitor extends Visitor {

    public void calculateScore(
            String party_id,
            QuestionModel question,
            Long iteration,
            GradingStrategy strategy,
            IChoiceDictionary choices,
            IScoreDictionary scores
    ) {
        HashMap<String, Score> playerScoreList = strategy.calculateScore(
                question,
                choices.getChoices(
                        party_id,
                        question.getQid()
                ),
                scores.getScore(party_id, iteration));
        scores.setScore(party_id, playerScoreList);
    }

    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.QStatisticsStateEvent.SEND_RESULT -> {
                System.out.println("At QStatisticsState, send result event occurred.");

                ChoiceDictionary choices = (ChoiceDictionary) params.get(GameConfig.ParamName.CHOICE_DICTIONARY);
                ScoreDictionary scores = (ScoreDictionary) params.get(GameConfig.ParamName.SCORE_DICTIONARY);

                calculateScore(
                        gameExecutor.getGameID(),
                        (QuestionModel) params.get(GameConfig.ParamName.QUESTION),
                        gameExecutor.getCurrentQuestionCnt(),
                        (GradingStrategy) params.get(GameConfig.ParamName.GRADING_STRATEGY),
                        choices,
                        scores
                );
                // TO DO
                HashMap<String, Object> result = new HashMap<>();
                result.put(GameConfig.ParamName.CHOICE_DICTIONARY, scores.getScore(
                        gameExecutor.getGameID(),
                        gameExecutor.getCurrentQuestionCnt()
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
