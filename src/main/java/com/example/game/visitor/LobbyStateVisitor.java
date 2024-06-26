package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.datacontainer.implementations.GameDataDictionary;
import com.example.game.datacontainer.implementations.PlayerDictionary;
import com.example.game.datacontainer.implementations.ScoreDictionary;
import com.example.game.entities.Player;

import com.example.game.model.GameSettingsModel;
import com.example.game.model.QuestionModel;
import java.util.HashMap;
import org.springframework.messaging.simp.user.SimpUserRegistry;

public class LobbyStateVisitor extends Visitor {
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        HashMap<String, Object> result = new HashMap<>();
        switch (event) {
            case GameConfig.LobbyStateEvent.GET_ACCESS_CODE -> {
                System.out.println("At LobbyState, get access code event occurred.");
                // TO DO
            }
            case GameConfig.LobbyStateEvent.START_GAME -> {
                System.out.println("At LobbyState, start game event occurred.");
                // TO DO
                GameDataDictionary gameDataDictionary =
                    (GameDataDictionary) params.get(GameConfig.ParamName.GAME_DATA_DICTIONARY);
                GameSettingsModel settings =
                    (GameSettingsModel) params.get(ParamName.GAME_SETTINGS);
                gameExecutor.setCurrentQuestionCnt(1L);
                QuestionModel question = gameDataDictionary.get(
                    gameExecutor.getGameID(),
                    gameExecutor.getCurrentQuestionCnt(),
                    gameExecutor.getCurrentQuestionCnt() + 1
                );
                result.put(ParamName.QUESTION, question);
                gameExecutor.getState().toNextState(GameConfig.LobbyStateEvent.START_GAME);
            }
            case GameConfig.LobbyStateEvent.REGISTER -> {
                SimpUserRegistry simpUserRegistry =
                    (SimpUserRegistry) params.get(GameConfig.ParamName.SIMP_USER_REGISTRY);
                System.out.println("At LobbyState, register event occurred.");
                System.out.print("The name of the player: ");
                System.out.println(params.get(GameConfig.ParamName.NAME).toString());
                String playerId = (String) params.get(ParamName.WS_USER_NAME);
                if (playerId != null) {
                    System.out.print("The name of the player: ");
                    System.out.println(playerId);
                }
                else {
                    System.out.println("The name of the player is null.");
                    playerId = params.get(GameConfig.ParamName.NAME).toString();
                }
                PlayerDictionary playerDictionary =
                        (PlayerDictionary) params.get(GameConfig.ParamName.PLAYER_DICTIONARY);
                ScoreDictionary scoreDictionary =
                        (ScoreDictionary) params.get(GameConfig.ParamName.SCORE_DICTIONARY);
                // TODO: Where can I get the Session field?
                Player newPlayer = new Player(
                    gameExecutor.getGameID(),
                    playerId
                );
                playerDictionary.registerPlayer(gameExecutor.getGameID(), newPlayer);
                scoreDictionary.createScore(gameExecutor.getGameID(), newPlayer.getName(), 0L);
                result.put(ParamName.PLAYER_ID, newPlayer.getName());
            }
        }
        result.put(ParamName.STATUS_PR, "success");
        return result;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
