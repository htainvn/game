package com.example.game.dto;

import java.util.ArrayList;

import com.example.game.dto.ReturnAnswerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.game.dto.OriginalQuestionDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReturnQuestionDto {
  public int index;
  public String question;
  private Integer time;
  public ArrayList<ReturnAnswerDto> answers;

  @Override
  public String toString() {
    return "GameQuestionDto{" +
        "index=" + index +
        ", question='" + question + '\'' +
        ", answers=" + answers +
        ", time=" + time +
        '}';
  }
}