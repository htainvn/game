package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.TempScoreDictionary;

import java.util.HashMap;

public class GameEndStateVisitor extends Visitor {
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.GameEndStateEvent.GET_FINAL_RANK -> {
                System.out.println("At GameEndState, game end event occurred.");
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
