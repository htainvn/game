package com.example.game.model;

import java.io.Serial;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionModel implements java.io.Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String party_id;
  private Integer qid;
  private String data;
  @Setter
  private ArrayList<AnswerModel> answers;
}
