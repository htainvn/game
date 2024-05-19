package com.example.game.model.decorator;

import com.example.game.config.GameConfig;
import com.example.game.config.GameConfig.ParamName;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
public class CoRRequest implements ICoRRequest {
  @Getter
  protected CoRData data;

  public CoRRequest() {
    this.data = new CoRData();
    this.data.reqs = new HashMap<>();
    this.data.params = new HashMap<>();
  }

  public CoRRequest(CoRRequest other) {
    this.data = new CoRData();
    this.data.reqs = (HashMap<String, Boolean>) other.data.reqs.entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                e -> (String) e.getKey(),
                e -> (Boolean) e.getValue()
            )
        );
    this.data.params = (HashMap<String, Object>) other.data.params.entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                e -> (String) e.getKey(),
                e -> (Object) e.getValue()
            )
        );
  }

  @Override
  public void putReq(String reqKey, boolean reqValue) {
    data.reqs.put(reqKey, reqValue);
  }

  @Override
  public void putParam(String paramKey, Object paramValue) {
    data.params.put(paramKey, paramValue);
  }

  @Override
  public QuestionModel extract() {
    return null;
  }

  @Override
  public CoRRequest clone() {
    return new CoRRequest(this);
  }

  @Override
  public boolean isValid() {
    return data.reqs.containsKey(ParamName.COR_FETCH);
  }
}
