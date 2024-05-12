package com.example.game.entities;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.example.game.dto.OriginalQuestionDto;

@NoArgsConstructor
@AllArgsConstructor
public class GameQuestionDto {
  public int index;
  public String question;
  public ArrayList<GameAnswerDto> answers;

  public GameQuestionDto(OriginalQuestionDto data) {
    this.index = data.index;
    this.question = data.question;
    this.answers = new ArrayList<>();
    data.answers.forEach((answer) -> {
      this.answers.add(new GameAnswerDto(answer));
    });
  }

  @Override
  public String toString() {
    return "GameQuestionDto{" +
        "index=" + index +
        ", question='" + question + '\'' +
        ", answers=" + answers +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    GameQuestionDto that = (GameQuestionDto) obj;
    return index == that.index && question.equals(that.question) && answers.equals(that.answers);
  }
}