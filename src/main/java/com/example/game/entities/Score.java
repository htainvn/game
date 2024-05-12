package com.example.game.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Score implements Serializable
{
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private String pid; //party id

  @Id
  private String player_id;

  private Long score;

}
