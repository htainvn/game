package com.example.game.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/*
"answers": [
                {
                    "index": 0,
                    "answer": "FTP",
                    "is_correct": false
                },
                {
                    "index": 1,
                    "answer": "HTTP",
                    "is_correct": false
                }
            ]
 */
@NoArgsConstructor
@AllArgsConstructor
public class OriginalAnswerDto {
  public int index;
  public String answer;
  public boolean is_correct;
}
