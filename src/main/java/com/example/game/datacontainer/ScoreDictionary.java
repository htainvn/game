package com.example.game.datacontainer;

import com.example.game.entities.Score;
import java.util.HashMap;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

@Component
public class ScoreDictionary {
  private HashMap<String, HashMap<String, Score>> scores; // party_id -> player_id -> score

  public Score getScore(String party_id, String player_id) {
    Score result = scores.get(party_id).get(player_id);
    return result;
  }

  private Score updateScore(String party_id, String player_id, Long scoreUp) {
    Score scoreObj = scores.get(party_id).get(player_id);
    scores.get(party_id).get(player_id).setScore(scoreObj.getScore() + scoreUp);
    return scores
        .get(party_id)
        .get(player_id);
  }

  public Score[] updateScoreOfGame(
      String party_id, HashMap<String,
      Pair<Boolean, Integer>> gameResults
  ) {
    gameResults.forEach(
        (player_id, result) -> {
          if (result.getValue0()) {
            updateScore(party_id, player_id, result.getValue1().longValue());
          }
        }
    );
    return scores.get(party_id).values().toArray(new Score[0]);
  }

  public HashMap<String, Score> getRanking(String party_id) {
    Stream<Score> sscore =  scores
                            .get(party_id)
                            .values()
                            .stream()
                            .sorted(
                                (s1, s2) -> s2.getScore().compareTo(s1.getScore())
                            );
    return sscore.collect(
        HashMap::new,
        (map, score) -> map.put(score.getPlayer_id(), score),
        HashMap::putAll
    );
  }
}
