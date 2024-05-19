package com.example.game.model.decorator;

import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.AnswerModel;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class QFRequest extends CoRRequest {

  public QFRequest(CoRRequest other) {
    super();
    this.data = other.clone().data;
  }

  @Override
  public QuestionModel extract() {
    return (QuestionModel) this.data.params.get(ParamName.COR_RESULT);
  }
}