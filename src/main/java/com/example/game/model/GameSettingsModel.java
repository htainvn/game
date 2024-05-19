package com.example.game.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameSettingsModel implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String party_id;
  private String gameFlowMode;
  private String gradingStrategy;
  private boolean question_randomized;
  private boolean answer_randomized;
}
