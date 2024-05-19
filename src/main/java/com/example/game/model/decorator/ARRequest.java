package com.example.game.model.decorator;

import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.AnswerModel;
import com.example.game.model.QuestionModel;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ARRequest extends QFRequest {
  public ARRequest(QFRequest other) {
    super();
    this.data = other.clone().data;
  }

  @Override
  public QuestionModel extract() {
    return (QuestionModel) this.data.params.get(ParamName.COR_RESULT);
  }
}
