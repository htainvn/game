package com.example.game.services;

import com.example.game.entities.Question;
import java.util.ArrayList;

public interface GInfoInterface {
  public Question getQuestion(String pid, String question_id);

  public String getGameCode(String pid);

  public void deleteGame(String pid);

  public void saveGameData(ArrayList<Question> questions);


}