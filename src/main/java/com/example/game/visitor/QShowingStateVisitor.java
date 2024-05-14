package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.entities.Question;
import com.example.game.executor.GameExecutor;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class QShowingStateVisitor extends Visitor{
    @Override
    public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
        GameExecutor gameExecutor =
                params.get("gameExecutor") instanceof GameExecutor ?
                        (GameExecutor) params.get("gameExecutor") :
                        null;
        if (gameExecutor == null) {
            return null;
        }
        HashMap<String, Object> result = new HashMap<>();

        switch (event) {
            case GameConfig.QShowingStateEvent.SHOW_QUESTION -> {
                System.out.println("At QShowingState, show question event occurred. Moving to QAnsweringState.");
                int time = (int) params.get("time");
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
                executor.execute(() -> {
                    try {
                        Thread.sleep(time * 1000L);
                        HashMap<String, Object> new_params;
                        new_params = new HashMap<>();
                        new_params.put("event", GameConfig.QShowingStateEvent.TIME_OUT);
                        gameExecutor.execute(new_params);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                });
                // get question and return
                Question question = new Question();
                result.put(GameConfig.ParamName.QUESTION, question);
                result.put(GameConfig.ParamName.QUESTION_TIME_OUT, question.getShowingTime());
                result.put(GameConfig.ParamName.CURRENT_QUESTION_CNT, gameExecutor.getCurrentQuestionCnt());
                return result;
            }
            case GameConfig.QShowingStateEvent.TIME_OUT -> {
                System.out.println("At QShowingState, time out event occurred. Moving to QAnsweringState.");
                gameExecutor.getState().toNextState(GameConfig.QShowingStateEvent.TIME_OUT);
            }
            case GameConfig.QShowingStateEvent.ANSWER_QUESTION -> {
                System.out.println("At QShowingState, answer question event occurred. Moving to QAnsweringState.");
                // TO DO
                gameExecutor.getState().toNextState(GameConfig.QShowingStateEvent.ANSWER_QUESTION);
            }
            default -> {
                System.out.println("At QShowingState, unknown event occurred.");
            }
        }
        return null;
    }

    @Override
    public HashMap<String, Object> doWithMaxCorrectGame(String event, HashMap<String, Object> params) {
        return doWithTimeUpGame(event, params);
    }
}
