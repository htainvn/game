package com.example.game.keys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class QuestionKey implements Serializable {
    private static final long serialVersionUID = 1L;
    public String qid;
}
