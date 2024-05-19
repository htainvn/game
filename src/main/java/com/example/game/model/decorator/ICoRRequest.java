package com.example.game.model.decorator;

import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;

public interface ICoRRequest {
  void putReq(String reqKey, boolean reqValue);
  void putParam(String paramKey, Object paramValue);
  QuestionModel extract();
  CoRRequest clone();
  public boolean isValid();
}
