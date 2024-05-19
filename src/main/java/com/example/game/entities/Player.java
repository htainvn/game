package com.example.game.entities;

import jakarta.websocket.Session;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String pid; //party id

  private String name;
}
