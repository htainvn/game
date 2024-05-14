package com.example.game.visitor;

import com.example.game.config.GameConfig;

import java.util.HashMap;

public class GameRankingStateVisitor extends Visitor{
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.GameRankingStateEvent.GET_RANKING -> {
                System.out.println("At GameRankingState, get ranking event occurred.");
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
