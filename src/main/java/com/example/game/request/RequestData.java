package com.example.game.request;


import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestData implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String gameID;
  private String requestType;
  private String payload;
}
