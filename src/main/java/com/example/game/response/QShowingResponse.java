package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QShowingResponse extends Response {
  private String gameID;
  private Integer questionNumber;
  private Integer questionType;
  private String data;
  private String answer;
  private Integer timeLimit;

  public QShowingResponse() {
    super();
  }

  public QShowingResponse(
      String gameID,
      Integer questionNumber,
      Integer questionType,
      String data,
      String answer,
      Integer timeLimit
  ) {
    super(true, GameConfig.QUESTION_SHOWING_CODE, "Question showing successfully");
    this.gameID = gameID;
    this.questionNumber = questionNumber;
    this.questionType = questionType;
    this.data = data;
    this.answer = answer;
    this.timeLimit = timeLimit;
  }
}