package com.example.game.entities;

import com.example.game.dto.OriginalAnswerDto;
import com.example.game.entities.key.AnswerKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ganswer")
@IdClass(AnswerKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Answer implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private Long aid;

  @Column
  private String content;

  @Column
  private Boolean isCorrect;

  @Setter
  @ManyToOne
//  @Column(insertable=false, updatable=false)
  @JoinColumns({
      @JoinColumn(name = "qid", referencedColumnName = "qid"),
      @JoinColumn(name = "game_id", referencedColumnName = "game_id")
  })
  @Id
  private Question question;

  public Answer(OriginalAnswerDto answerDto, Question question) {
    this.aid = (long) answerDto.index;
    this.content = answerDto.answer;
    this.isCorrect = answerDto.is_correct;
    this.question = question;
  }

  public Answer takeSnapshot() {
    Answer snapshot = new Answer();
    snapshot.aid = this.aid;
    snapshot.content = this.content;
    snapshot.isCorrect = this.isCorrect;
    snapshot.question = this.question;
    return snapshot;
  }

  public void recovery(Answer snapshot) {
    this.aid = snapshot.aid;
    this.content = snapshot.content;
    this.isCorrect = snapshot.isCorrect;
    this.question = snapshot.question;
  }
}
