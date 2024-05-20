package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.datacontainer.implementations.ChoiceDictionary;
import com.example.game.dto.GameChoice;
import com.example.game.state.QStatisticsState;

import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class QAnsweringStateVisitor extends Visitor{

    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {

        switch (event) {
            case GameConfig.QAnsweringStateEvent.INITIALIZE -> {
                System.out.println("At QAnsweringState, initialize event occurred.");
                startTimeoutThread(params);
            }
            case GameConfig.QAnsweringStateEvent.SEND_CHOICE -> {
                System.out.println("At QAnsweringState, send choice event occurred.");
                ChoiceDictionary choices = (ChoiceDictionary) params.get(GameConfig.ParamName.CHOICE_DICTIONARY);
                String player_id = (String) params.get(GameConfig.ParamName.PLAYER_ID);
                System.out.print("At this stage 3, the name: ");
                System.out.println(params.get(GameConfig.ParamName.PLAYER_ID));
                Long answer_id = (Long) params.get(GameConfig.ParamName.ANSWER_ID);
                Long time = (Long) params.get(GameConfig.ParamName.ANSWERED_TIME);
                choices.addChoice(
                    new GameChoice(
                        gameExecutor.getGameID(),
                        player_id,
                        gameExecutor.getCurrentQuestionID(),
                        answer_id,
                        time
                    )
                );
            }
            case GameConfig.QAnsweringStateEvent.TIME_OUT -> {
                System.out.println("At QAnsweringState, timeout event occurred. Moving to QStatisticsState.");
                gameExecutor.setState(new QStatisticsState());
            }
            case GameConfig.QAnsweringStateEvent.EXCEED_MAX_CORRECT -> {
                System.out.println("At QAnsweringState, exceed max correct event occurred but nothing happened because of using TimeUpGame.");
            }
            case GameConfig.QAnsweringStateEvent.SKIP -> {
                ThreadPoolExecutor executor = gameExecutor.getTimeoutThread();
                System.out.println("At QAnsweringState, skip event occurred. Moving to QStatisticsState.");
                if (executor != null) {
                    executor.shutdownNow();
                }
                gameExecutor.setState(new QStatisticsState());
            }
        }
        return null;
    }

    private void startTimeoutThread(HashMap<String, Object> params) {
        ThreadPoolExecutor executor = gameExecutor.getTimeoutThread();
        int time = (int) params.get(GameConfig.ParamName.ANSWERED_TIME);
        executor.execute(() -> {
            try {
                Thread.sleep(time * 1000L);
                HashMap<String, Object> new_params;
                new_params = new HashMap<>();
                new_params.put("event", GameConfig.QAnsweringStateEvent.TIME_OUT);
                gameExecutor.execute(new_params);
            } catch (InterruptedException e) {
                System.out.println("Thread Answering interrupted");
            }
        });
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        switch (event) {
            case GameConfig.QAnsweringStateEvent.INITIALIZE -> {
                System.out.println("At QAnsweringState, initialize event occurred.");
                startTimeoutThread(params);
            }
            case GameConfig.QAnsweringStateEvent.SEND_CHOICE -> {
                System.out.println("At QAnsweringState, send choice event occurred.");
                // check how many correct answers the player has
                if (true) {
                    HashMap<String, Object> new_params;
                    new_params = new HashMap<>();
                    new_params.put("event", GameConfig.QAnsweringStateEvent.EXCEED_MAX_CORRECT);
                    gameExecutor.execute(new_params);
                }
            }
            case GameConfig.QAnsweringStateEvent.TIME_OUT -> {
                System.out.println("At QAnsweringState, timeout event occurred but nothing happened because of using MaxCorrectGame.");
            }
            case GameConfig.QAnsweringStateEvent.EXCEED_MAX_CORRECT -> {
                System.out.println("At QAnsweringState, exceed max correct event occurred. Moving to QStatisticsState.");
                gameExecutor.setState(new QStatisticsState());
            }
            case GameConfig.QAnsweringStateEvent.SKIP -> {
                ThreadPoolExecutor executor = gameExecutor.getTimeoutThread();
                System.out.println("At QAnsweringState, skip event occurred. Moving to QStatisticsState.");
                if (executor != null) {
                    executor.shutdownNow();
                }
                gameExecutor.setState(new QStatisticsState());
            }
        }
        return null;
    }
}
