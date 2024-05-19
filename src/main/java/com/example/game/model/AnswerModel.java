package com.example.game.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerModel implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String id;
  private String content;
  private boolean correct;
}
