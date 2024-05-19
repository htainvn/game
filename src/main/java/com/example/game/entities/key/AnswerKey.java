package com.example.game.entities.key;

import com.example.game.entities.Question;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AnswerKey implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Question question;
  private Long aid;
}
