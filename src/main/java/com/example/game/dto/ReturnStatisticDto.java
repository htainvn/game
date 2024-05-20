package com.example.game.dto;

import lombok.AllArgsConstructor;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
public class ReturnStatisticDto {
    ArrayList<Long> countAnswer;
    String correctAnswerID;
    HashMap<String, Pair<Boolean, Integer>> playerAnswer; // key: playerID, value: <isCorrect, bonusPoint>

    @Override
    public String toString() {
        return super.toString();
    }
}
