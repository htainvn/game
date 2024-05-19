package com.example.game.entities;

import com.ethlo.time.DateTime;
import com.example.game.dto.OriginalQuizDto;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GameQuizDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  public String gameid;
  public String title;
  public Long expiry;
  public ArrayList<GameQuestionDto> questions;

  private static final int TTL = 1000 * 60 * 60 * 24;

  public GameQuizDto(Game data) {
    this.gameid = data.getGame_id();
    this.title = "";
    Date today = new Date();
    this.expiry = (long) (today.getTime() + TTL);
    this.questions = new ArrayList<>();
    if (data.getQuestions() != null) {
      data.getQuestions().forEach((question) -> {
        this.questions.add(new GameQuestionDto(question));
      });
    }
  }
}
