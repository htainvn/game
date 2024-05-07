package com.example.game.services;

import com.example.game.entities.Question;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GInfoService implements GInfoInterface {



  @Autowired
  public GInfoService() {

  }

  @Override
  public Question getQuestion(String pid, String question_id) {
    return null;
  }

  @Override
  public String getGameCode(String pid) {
    return null;
  }

  @Override
  public void deleteGame(String pid) {

  }

  @Override
  public void saveGameData(ArrayList<Question> questions) {

  }
}
