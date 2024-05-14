package com.example.game.response;

import com.example.game.config.GameConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRegisteredResponse extends Response {
    private String registerStatus;
    private String player_id;

public GameRegisteredResponse() {
        super();
    }

    public GameRegisteredResponse(String status, String player_id) {
        super(true, GameConfig.PLAYER_REGISTERED_CODE, "Player registered successfully");
        this.registerStatus = status;
        this.player_id = player_id;
    }
}
