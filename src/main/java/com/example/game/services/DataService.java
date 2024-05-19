package com.example.game.services;

import com.example.game.config.GameConfig.DataParamName;
import com.example.game.config.GameConfig.DataServiceType;
import com.example.game.entities.Answer;
import com.example.game.entities.Game;
import com.example.game.entities.Question;
import com.example.game.entities.key.QuestionKey;
import com.example.game.repository.AnswerRepository;
import com.example.game.repository.GameRepository;
import com.example.game.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Service;

@Service
public class DataService {

  private GameRepository gameRepo;
  private QuestionRepository questionRepo;
  private AnswerRepository answerRepo;
  private DataSource dataSource;

  public DataService(GameRepository gameRepo, QuestionRepository questionRepo,
      AnswerRepository answerRepo) {
    this.gameRepo = gameRepo;
    this.questionRepo = questionRepo;
    this.answerRepo = answerRepo;
  }

  @Transactional
  public void store(String tos, HashMap<String, Object> args) {
    switch (tos) {
      case DataServiceType.PERSIST_GAME -> {
          Game game = (Game) args.get(DataParamName.GAME);
          Game snapshot = game.takeSnapshot();
          List<Question> savedQuestions = game.getQuestions();
          List<Answer> savedAnswers = new ArrayList<>();
          gameRepo.saveAndFlush(game);
          game.recovery(snapshot);
//          savedQuestions.forEach(question -> {
//            if (gameRepo.findById(game.getGame_id()).isPresent()) {
//              Game game1 = gameRepo.findById(game.getGame_id()).get();
//              question.setGame(game1);
//              savedAnswers.addAll(question.getAnswers());
//            }
//          });
//          questionRepo.saveAllAndFlush(savedQuestions);
//          savedAnswers.forEach(answer -> {
//            if (questionRepo.findById(answer.getQuestion().getKey()).isPresent()) {
//              Question question = questionRepo.findById(answer.getQuestion().getKey()).get();
//              answer.setQuestion(question);
//            }
//          });
//          answerRepo.saveAll(savedAnswers);
//          savedQuestions.forEach(question -> {
//            question.setGame(game);
//            if (gameRepo.findById(game.getGame_id()).isPresent()) {
//              Game game1 = gameRepo.findById(game.getGame_id()).get();
//              question.setGame(game1);
//              question.setAnswers(null);
//            }
//            questionRepo.save(question);
//          });
//          savedQuestions.forEach(question -> {
//            if (questionRepo.findById(question.getKey()).isPresent()) {
//              Question question1 = questionRepo.findById(question.getQid()).get();
//              question.setAnswers(null);
//              question.setGame(null);
//              question.setGame(question1.getGame());
//              question.setAnswers(null);
//              questionRepo.save(question);
//            }
//          });
      }
    }
  }

  public HashMap<String, Object> get(String tos, HashMap<String, Object> args) {
    HashMap<String, Object> result = new HashMap<>();
    switch (tos) {
      case "question" -> {
        String party_id = (String) args.get("party_id");
        Long qid = (Long) args.get("qid");
        gameRepo.findById(party_id)
            .flatMap(game -> questionRepo.findById(new QuestionKey(qid, game)))
            .ifPresent(question -> result.put("question", question));
      }
    }
    return result;
  }
}
