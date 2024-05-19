package com.example.game.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameChoice {
  private String party_id;  // party id
  private String player_id; // player id
  private Long qid;       // question id
  private Long aid;         // answer id
  private Integer time;        // time submitted
}
