package com.example.game.datacontainer.interfaces;

import com.example.game.entities.GameQuizDto;
import com.example.game.entities.Question;
import com.example.game.model.GameSettingsModel;
import com.example.game.model.QuestionModel;

public interface IGameDataDictionary {
  public QuestionModel get(String party_id, Long qid, Long nqid);

  public void store(String party_id, GameSettingsModel settings);

  public void contains(String game_id, String creator_id);
}
