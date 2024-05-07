package com.example.game.datacontainer;

import com.example.game.dto.GameChoice;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class ChoiceDictionary {
  private HashMap<String, HashMap<Integer, ArrayList<GameChoice>>> choices;
  /*
  party id -> question id -> list of choices
   */

  public ChoiceDictionary() {
    choices = new HashMap<String, HashMap<Integer, ArrayList<GameChoice>>>();
  }

  public void addChoice(GameChoice choice) {
    if (!choices.containsKey(choice.getPid())) {
      choices.put(choice.getPid(), new HashMap<Integer, ArrayList<GameChoice>>());
    }
    if (!choices.get(choice.getPid()).containsKey(choice.getQid())) {
      choices.get(choice.getPid()).put(Integer.valueOf(choice.getQid()), new ArrayList<GameChoice>());
    }
    choices.get(choice.getPid()).get(choice.getQid()).add(choice);
  }

  public ArrayList<GameChoice> getChoices(String pid, int qid) {
    return choices.get(pid).get(qid);
  }

  public void removeAllChoicesFromParty(String pid) {
    choices.remove(pid);
  }

}
