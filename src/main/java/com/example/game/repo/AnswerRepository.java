package com.example.game.repo;

import com.example.game.entities.Answer;
import com.example.game.keys.AnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {
}
