package com.example.game.entities;

import com.example.game.dto.OriginalQuestionDto;
import com.example.game.dto.OriginalQuizDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Game implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  private String game_id;

  @Setter
  @OneToMany(mappedBy = "game", cascade = {CascadeType.ALL})
  private List<Question> questions;

  public Game(OriginalQuizDto orginalQuizDto) {
    this.game_id = UUID.randomUUID().toString();
    this.questions = new ArrayList<>();
    for (OriginalQuestionDto questionDto : orginalQuizDto.questions) {
      this.questions.add(new Question(questionDto, this));
    }
  }

  public Game takeSnapshot() {
    Game snapshot = new Game();
    snapshot.game_id = this.game_id;
    snapshot.setQuestions(new ArrayList<>());
    for (Question question : this.questions) {
      snapshot.getQuestions().add(question.takeSnapshot());
    }
    return snapshot;
  }

  public void recovery(Game snapshot) {
    this.game_id = snapshot.game_id;
    this.questions = new ArrayList<>();
    for (Question question : snapshot.questions) {
      Question q = new Question();
      q.recovery(question);
      this.questions.add(q);
    }
  }
}
