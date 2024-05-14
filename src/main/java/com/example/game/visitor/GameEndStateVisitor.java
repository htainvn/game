package com.example.game.visitor;

import com.example.game.config.GameConfig;

import java.util.HashMap;

public class GameEndStateVisitor extends Visitor {
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.GameEndStateEvent.GET_FINAL_RANK -> {
                System.out.println("At GameEndState, game end event occurred.");
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
