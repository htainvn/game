package com.example.game.datacontainer;

import com.example.game.dto.GameChoice;
import com.example.game.dto.GameChoiceCacheObject;
import com.example.game.services.DataService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChoiceDictionary {
  private HashMap<String, HashMap<String, ArrayList<GameChoice>>> choices;

  private HashMap<String, HashMap<String, HashMap<String, GameChoiceCacheObject>>> choicesv2;
  /*
  party id -> question id -> list of choices
   */

  @Autowired
  private DataService dataService;

  public GameChoice get(
      String party_id,
      String qid,
      String player_id
  ) {
    GameChoiceCacheObject result = choicesv2.get(party_id).get(qid).get(player_id);
    Date now = new Date();
    Date expirationTime = new Date(result.getLastUpdatedTime() + result.getTimeToLive());
    if (now.after(expirationTime)) {
      choicesv2.get(party_id).get(qid).remove(player_id);
      return null;
    }
    return result.get();
  }

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
