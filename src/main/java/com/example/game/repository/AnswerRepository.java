package com.example.game.repository;

import com.example.game.entities.Answer;
import com.example.game.entities.key.AnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {
}
