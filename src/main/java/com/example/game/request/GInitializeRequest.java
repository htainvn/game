package com.example.game.request;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GInitializeRequest {
  private String authHeader;
  private String quizID;
  private String authID;
  private String gameFlowMode;
  private String gradingStrategy;

  public GInitializeRequest(GInitializeRequest other) {
    this.authHeader = other.getAuthHeader();
    this.quizID = other.getQuizID();
    this.authID = other.getAuthID();
    this.gameFlowMode = other.getGameFlowMode();
    this.gradingStrategy = other.getGradingStrategy();
  }

  public GInitializeRequest(String json) {
    Gson gson = new Gson();
    GInitializeRequest request = gson.fromJson(json, GInitializeRequest.class);
    this.authHeader = request.getAuthHeader();
    this.quizID = request.getQuizID();
    this.authID = request.getAuthID();
    this.gameFlowMode = request.getGameFlowMode();
    this.gradingStrategy = request.getGradingStrategy();
  }
}
