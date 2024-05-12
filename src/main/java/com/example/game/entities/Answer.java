package com.example.game.entities;

import com.example.game.keys.AnswerKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;

@Entity
@Table
public class Answer implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter
  @Id
  private AnswerKey id;

  @Column
  private String content;

  @Getter
  @Column
  private Boolean isCorrect;

  @ManyToOne
//  @Column(insertable=false, updatable=false)
  @JoinColumn(name = "qid")
  private Question question;
}
