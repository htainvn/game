package com.example.game.dto;

import java.io.Serial;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameChoice implements java.io.Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String party_id;  // party id
  private String player_id; // player id
  private Long qid;       // question id
  private Long aid;         // answer id
  private Long time;        // time submitted
}
