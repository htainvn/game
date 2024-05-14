package com.example.game.visitor;

import com.example.game.config.GameConfig;

import java.util.HashMap;

public class LobbyStateVisitor extends Visitor {
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.LobbyStateEvent.GET_ACCESS_CODE -> {
                System.out.println("At LobbyState, get access code event occurred.");
                // TO DO
            }
            case GameConfig.LobbyStateEvent.START_GAME -> {
                System.out.println("At LobbyState, start game event occurred.");
                // TO DO
            }
            case GameConfig.LobbyStateEvent.REGISTER -> {
                System.out.println("At LobbyState, register event occurred.");
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
