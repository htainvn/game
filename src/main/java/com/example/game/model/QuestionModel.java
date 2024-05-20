package com.example.game.model;

import com.example.game.entities.Question;
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
  private Long qid;
  private String data;
  @Setter
  private ArrayList<AnswerModel> answers;
  private Long time;

  public QuestionModel(Question question) {
    this.party_id = question.getGame().getGame_id();
    this.qid = question.getQid();
    this.data = question.getStatement();
    this.time = question.getTime();
    answers = new ArrayList<>();
    question.getAnswers().forEach(answer -> answers.add(new AnswerModel(answer)));
  }

  public Long getCorrectAnswer() {
    for (AnswerModel answer : answers) {
      if (answer.isCorrect()) {
        return answer.getId();
      }
    }
    return null;
  }
}
