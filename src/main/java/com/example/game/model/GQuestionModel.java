package com.example.game.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GQuestionModel {
  public Integer iteration;
  public String party_id;
  public Integer qid;
  public String data;
  public ArrayList<AnswerModel> answers;
}
