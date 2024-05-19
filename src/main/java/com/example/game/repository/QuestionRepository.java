package com.example.game.repository;

import com.example.game.entities.Question;
import com.example.game.entities.key.QuestionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, QuestionKey> {
}
