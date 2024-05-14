package com.example.game.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QShowingResponse extends Response {
  private String gameID;
  private Integer questionNumber;
  private Integer questionType;
  private String data;
  private String answer;
  private Integer timeLimit;
  private Integer timeout;

}