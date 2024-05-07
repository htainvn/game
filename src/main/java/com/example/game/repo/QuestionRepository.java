package com.example.game.repo;

import com.example.game.entities.Question;
import com.example.game.keys.QuestionKey;
import org.javatuples.Pair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, QuestionKey> {
}
