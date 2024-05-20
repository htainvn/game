package com.example.game.model;

import com.example.game.entities.Answer;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerModel implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String content;
  private boolean correct;

  public AnswerModel(Answer answer) {
    this.id = answer.getAid();
    this.content = answer.getContent();
    this.correct = answer.getIsCorrect();
  }
}
