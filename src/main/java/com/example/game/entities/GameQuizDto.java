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

  private static final long TTL = 1000 * 60 * 60 * 24;

  public String quiz_id;
  public String auth_id;
  public String title;
  public int expiry;
  public ArrayList<GameQuestionDto> questions;

  public GameQuizDto(OriginalQuizDto data) {
    this.quiz_id = data.quiz_id;
    this.auth_id = data.auth_id;
    this.title = data.title;
    Date today = new Date();
    this.expiry = (int) (today.getTime() + TTL);
    this.questions = new ArrayList<>();
    data.questions.forEach((question) -> {
      this.questions.add(new GameQuestionDto(question));
    });
  }

  @Override
  public String toString() {
    return "GameQuizDto{" +
        "quiz_id='" + quiz_id + '\'' +
        ", auth_id='" + auth_id + '\'' +
        ", title='" + title + '\'' +
        ", expiry=" + expiry +
        ", questions=" + questions +
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
    GameQuizDto that = (GameQuizDto) obj;
    return quiz_id.equals(that.quiz_id) && auth_id.equals(that.auth_id) && title.equals(that.title) && expiry == that.expiry && questions.equals(that.questions);
  }
}
