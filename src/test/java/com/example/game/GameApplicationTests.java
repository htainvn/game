package com.example.game;

import com.example.game.dto.OriginalQuizDto;
import com.example.game.entities.GameQuizDto;
import com.example.game.helper.RandomCode;
import com.example.game.redis.RedisService;
import com.example.game.state.LobbyState;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameApplicationTests {

	@Autowired
	RedisService redisService;

	@Test
	void contextLoads() {
		String json = """
				{
				    "quiz_id": "meomeo",
				    "auth_id": "1234567890",
				    "title": "Internetworking Protocols Quiz",
				    "description": "Test your knowledge about internetworking protocols.",
				    "created_at": 1715159663633,
				    "num_questions": 5,
				    "questions": [
				        {
				            "index": 0,
				            "question": "Which protocol is used for transferring files over a network?",
				            "time_limit": 60,
				            "allow_powerups": false,
				            "question_type": 0,
				            "answers": [
				                {
				                    "index": 0,
				                    "answer": "FTP",
				                    "is_correct": true
				                },
				                {
				                    "index": 1,
				                    "answer": "HTTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 2,
				                    "answer": "SMTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 3,
				                    "answer": "DNS",
				                    "is_correct": false
				                }
				            ]
				        },
				        {
				            "index": 1,
				            "question": "Which protocol is used for sending email?",
				            "time_limit": 60,
				            "allow_powerups": false,
				            "question_type": 0,
				            "answers": [
				                {
				                    "index": 0,
				                    "answer": "FTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 1,
				                    "answer": "HTTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 2,
				                    "answer": "SMTP",
				                    "is_correct": true
				                },
				                {
				                    "index": 3,
				                    "answer": "DNS",
				                    "is_correct": false
				                }
				            ]
				        },
				        {
				            "index": 2,
				            "question": "Which protocol is used for resolving domain names to IP addresses?",
				            "time_limit": 60,
				            "allow_powerups": false,
				            "question_type": 0,
				            "answers": [
				                {
				                    "index": 0,
				                    "answer": "FTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 1,
				                    "answer": "HTTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 2,
				                    "answer": "SMTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 3,
				                    "answer": "DNS",
				                    "is_correct": true
				                }
				            ]
				        },
				        {
				            "index": 3,
				            "question": "True or False: TCP is a connectionless protocol.",
				            "time_limit": 60,
				            "allow_powerups": false,
				            "question_type": 1,
				            "answers": [
				                {
				                    "index": 0,
				                    "answer": "True",
				                    "is_correct": false
				                },
				                {
				                    "index": 1,
				                    "answer": "False",
				                    "is_correct": true
				                }
				            ]
				        },
				        {
				            "index": 4,
				            "question": "Which protocol is used for routing packets on the internet?",
				            "time_limit": 60,
				            "allow_powerups": false,
				            "question_type": 0,
				            "answers": [
				                {
				                    "index": 0,
				                    "answer": "FTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 1,
				                    "answer": "HTTP",
				                    "is_correct": false
				                },
				                {
				                    "index": 2,
				                    "answer": "IP",
				                    "is_correct": true
				                },
				                {
				                    "index": 3,
				                    "answer": "DNS",
				                    "is_correct": false
				                }
				            ]
				        }
				    ]
				}
				""";
//		Gson gson = new Gson();
//		OriginalQuizDto originalQuizDto = gson.fromJson(json, OriginalQuizDto.class);
//		GameQuizDto gameQuizDto = new GameQuizDto(originalQuizDto);
//		redisService.save("meomeo", gameQuizDto);

//		try {
//			Thread.sleep(5000);
//			GameQuizDto data = redisService.find("meomeo");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		System.out.println(LobbyState.class.getName());
		assert(LobbyState.class.toString().equals(String.copyValueOf(
        "com.example.game.state.LobbyState".toCharArray())));
//		}
	}

}
