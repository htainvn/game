package com.example.game.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/*
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
 */
@NoArgsConstructor
@AllArgsConstructor
public class OriginalQuizDto {
  public String quiz_id;
  public String auth_id;
  public String title;
  public String description;
  public long created_at;
  public int num_questions;
  public ArrayList<OriginalQuestionDto> questions;
}
