package com.example.game.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMessage {
  private Date timestamp;
  private String service;
  private String body;
}
