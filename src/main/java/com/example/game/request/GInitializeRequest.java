package com.example.game.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GInitializeRequest {
  private String quizID;
  private String authID;
  private String gameFlowMode;
  private String gradingStrategy;
}
