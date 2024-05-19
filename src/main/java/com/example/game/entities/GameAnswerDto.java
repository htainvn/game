package com.example.game.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.example.game.dto.OriginalAnswerDto;

@NoArgsConstructor
@AllArgsConstructor
public class GameAnswerDto {
  public int index;
  public String answer;
  public boolean is_correct;

  public GameAnswerDto(Answer data) {
    this.index = Math.toIntExact(data.getAid());
    this.answer = data.getContent();
    this.is_correct = data.getIsCorrect();
  }

  @Override
  public String toString() {
    return "GameAnswerDto{" +
        "index=" + index +
        ", answer='" + answer + '\'' +
        ", is_correct=" + is_correct +
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
    GameAnswerDto that = (GameAnswerDto) obj;
    return index == that.index && answer.equals(that.answer) && is_correct == that.is_correct;
  }
}
