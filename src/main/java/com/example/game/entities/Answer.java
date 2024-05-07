package com.example.game.entities;

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
  private String pid; //party id

  @Getter
  @Id
  private Long id;

  @Column
  private String content;

  @Column
  private Boolean isCorrect;

  @ManyToOne
  @Id
  @JoinColumn(name = "qid")
  private Question question;

}
