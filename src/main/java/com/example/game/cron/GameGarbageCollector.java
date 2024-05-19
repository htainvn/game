package com.example.game.cron;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameGarbageCollector {

    private static final long ONE_DAYS = 24 * 60 * 60 * 1000;

    @Scheduled(cron = "0 0 7 * * ?")
    public void garbageCollect() {
      System.out.println("Garbage collection is running");
    }
}
