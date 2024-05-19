package com.example.game.visitor;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.DataParamName;
import com.example.game.config.GameConfig.DataServiceType;
import com.example.game.config.GameConfig.EmptyGameStateEvent;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.entities.Game;
import com.example.game.services.DataService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class EmptyGameStateVisitor extends Visitor {

  private DataService dataService;

  @Override
  public HashMap<String, Object> doWithTimeUpGame(String event, HashMap<String, Object> params) {
    switch (event) {
      case EmptyGameStateEvent.BIND -> {
        dataService = (DataService) params.get(ParamName.DATA_SERVICE);
        Game game = (Game) params.get(ParamName.DATA);
        params.put(DataParamName.GAME, game);
        dataService.store(DataServiceType.PERSIST_GAME, params);
        params.put("status", "success");
        gameExecutor.getState().toNextState(EmptyGameStateEvent.INITIALIZED);
      }
    }
    return params;
  }

  @Override
  public HashMap<String, Object> doWithMaxCorrectGame(String event,
      HashMap<String, Object> params) {
    return doWithTimeUpGame(event, params);
  }
}
