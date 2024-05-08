package com.example.game.dto;

import lombok.Getter;

@Getter
public class GameChoice {
  private String party_id;  // party id
  private String player_id; // player id
  private String qid;       // question id
  private Long aid;         // answer id
  private Long time;        // time submitted
}
