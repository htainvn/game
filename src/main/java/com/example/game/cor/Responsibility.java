package com.example.game.cor;

import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.CoRRequest;
import java.util.Optional;
import lombok.Setter;

public abstract class Responsibility {
  @Setter
  protected Responsibility next;
  public abstract QuestionModel handleRequest(CoRRequest request);
}
