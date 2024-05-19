package com.example.game.model.decorator;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.AnswerModel;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import java.util.ArrayList;
import lombok.Setter;

public class QTRequest extends ARRequest {

  public QTRequest(ARRequest other) {
    this.data = other.clone().data;
  }

  @Override
  public QuestionModel extract() {
    return (QuestionModel) this.data.params.get(ParamName.COR_RESULT);
  }
}
