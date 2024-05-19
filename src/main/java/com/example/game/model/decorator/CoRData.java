package com.example.game.model.decorator;

import com.example.game.model.AnswerModel;
import com.example.game.model.GQuestionModel;
import com.example.game.model.QuestionModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CoRData {
  public HashMap<String, Boolean> reqs;
  public HashMap<String, Object> params;
}
