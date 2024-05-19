package com.example.game.cor;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.AnswerModel;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.ARRequest;
import com.example.game.model.decorator.CoRRequest;
import com.example.game.model.decorator.QTRequest;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerRandomize extends Responsibility {

  public AnswerRandomize() {
    this.next = null;
  }

  @Override
  public QuestionModel handleRequest(CoRRequest request) {
    ARRequest arRequest = (ARRequest) request;
    Boolean isRandom = (Boolean) request.getData().reqs.get(ParamName.COR_ARANDOM);
    if (isRandom) {
      QuestionModel question = arRequest.extract();
      ArrayList<AnswerModel> answers = question.getAnswers();
      ArrayList<AnswerModel> newAnswers = new ArrayList<>();
      for (int i = 0; i < answers.size(); i++) {
        int randomIndex = (int) (Math.random() * answers.size());
        newAnswers.add(answers.get(randomIndex));
        answers.remove(randomIndex);
      }
      question.setAnswers(answers);
    }
    if (this.next != null) {
      QTRequest qtRequest = new QTRequest(arRequest);
      return this.next.handleRequest(qtRequest);
    } else {
      return arRequest.extract();
    }
  }
}
