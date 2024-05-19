package com.example.game.cor;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.datacontainer.implementations.GameDataDictionary;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import com.example.game.model.decorator.ARRequest;
import com.example.game.model.decorator.CoRRequest;
import com.example.game.model.decorator.QFRequest;
import com.example.game.services.DataService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionFetching extends Responsibility {

  public QuestionFetching() {
    this.next = new AnswerRandomize();
  }

  @Override
  public QuestionModel handleRequest(CoRRequest request) {
    QFRequest qfRequest = new QFRequest(request);
    if (this.next != null) {
      DataService dataService =
          (DataService) request.getData().params.get(ParamName.DATA_SERVICE);
      String gid = (String) request.getData().params.get(ParamName.PARTY_ID);
      Long qid = (Long) request.getData().params.get(ParamName.QUESTION_ID);
      Long nqid = (Long) request.getData().params.get(ParamName.NEXT_QUESTION_ID);
      HashMap<String, Object> args = new HashMap<>();
      args.put("party_id", gid);
      args.put("qid", qid);
      QuestionModel question = (QuestionModel) dataService.get("question", args).get("question");
      ARRequest arRequest = new ARRequest(qfRequest);
      return this.next.handleRequest(arRequest);
    } else {
      return qfRequest.extract();
    }
  }
}
