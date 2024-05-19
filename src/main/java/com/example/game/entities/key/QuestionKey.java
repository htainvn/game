package com.example.game.entities.key;


import com.example.game.entities.Game;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class QuestionKey implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Long qid;
  private Game game;
}
