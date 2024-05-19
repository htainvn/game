package com.example.game.cor;

import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.CoRRequest;
import org.springframework.stereotype.Component;

@Component
public class Validation extends Responsibility {

  public Validation() {
    super();
    next = new QuestionFetching();
  }

  @Override
  public QuestionModel handleRequest(CoRRequest request) {
    if (this.next != null) {
      if (request.isValid()) {
        return this.next.handleRequest(request);
      }
    }
    return request.extract();
  }
}
