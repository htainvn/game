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
  private Long questionNumber;
  private String data;
  private String answer;
  private Long timeLimit;
  private Long showingTime;
}