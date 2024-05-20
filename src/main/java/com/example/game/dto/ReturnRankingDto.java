package com.example.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
public class ReturnRankingDto {
    @Getter
    private HashMap<String, Integer> ranking; // key: playerID, value: score

    @Override
    public String toString() {
        return super.toString();
    }
}
