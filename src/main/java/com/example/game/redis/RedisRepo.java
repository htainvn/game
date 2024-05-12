package com.example.game.redis;

import com.example.game.entities.GameQuizDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepo extends CrudRepository<RedisObject, String> {
}
