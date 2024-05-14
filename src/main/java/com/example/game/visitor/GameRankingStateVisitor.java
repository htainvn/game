package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.TempScoreDictionary;

import java.util.HashMap;

public class GameRankingStateVisitor extends Visitor{
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.GameRankingStateEvent.GET_RANKING -> {
                System.out.println("At GameRankingState, get ranking event occurred.");
                // TO DO
                TempScoreDictionary scoreDictionary = (TempScoreDictionary) params.get(GameConfig.ParamName.SCORE_DICTIONARY);
                HashMap<String, Object> result = new HashMap<>();
                result.put(
                        GameConfig.ParamName.SCORE_DICTIONARY,
                        scoreDictionary.getRanking(gameExecutor.getGameID())
                );
                return result;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
