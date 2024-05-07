package com.example.game.dto;

import lombok.Getter;

@Getter
public class GameChoice {
  private String pid; //party id
  private String player_id;
  private String qid; //question id
  private Long aid; //answer id
}
