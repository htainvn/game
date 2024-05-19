package com.example.game.cor;

import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.CoRRequest;
import com.example.game.model.decorator.QTRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionTranslating extends Responsibility {
  public QuestionTranslating() {
    this.next = null;
  }

  @Override
  public QuestionModel handleRequest(CoRRequest request) {
    QTRequest qtRequest = (QTRequest) request;
    if (this.next != null) {
      CoRRequest nextReq = new CoRRequest(qtRequest);
      return this.next.handleRequest(qtRequest);
    } else {
      return qtRequest.extract();
    }
  }
}
