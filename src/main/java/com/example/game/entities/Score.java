package com.example.game.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Score implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  private String pid; //party id

  @Id
  private String player_id;

  private Long score;

}
