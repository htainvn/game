package com.example.game.datacontainer;

import com.example.game.dto.GameChoice;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class ChoiceDictionary {
  private HashMap<String, HashMap<String, ArrayList<GameChoice>>> choices;
  /*
  party id -> question id -> list of choices
   */

  public ChoiceDictionary() {
    choices = new HashMap<String, HashMap<String, ArrayList<GameChoice>>>();
  }

  public void addChoice(GameChoice choice) {
    if (!choices.containsKey(choice.getParty_id())) {
      choices.put(choice.getParty_id(), new HashMap<String, ArrayList<GameChoice>>());
    }
    if (!choices.get(choice.getParty_id()).containsKey(choice.getQid())) {
      choices.get(choice.getParty_id()).put(choice.getQid(), new ArrayList<GameChoice>());
    }
    choices.get(choice.getParty_id()).get(choice.getQid()).add(choice);
  }

  public ArrayList<GameChoice> getChoices(String party_id, String qid) {
    return choices.get(party_id).get(qid);
  }

  public void removeAllChoicesFromParty(String pid) {
    choices.remove(pid);
  }

}
