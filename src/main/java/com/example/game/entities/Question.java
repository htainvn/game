package com.example.game.entities;

import com.example.game.keys.QuestionKey;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Id
    private QuestionKey qid;

    @Getter
    @Column
    private String statement;

    @Getter
    @Column
    private Integer category;
    @Getter
    @Column
    private Long points;
    @Getter
    @Column
    private Long time;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Long getCorrectAnswer() {
        for (Answer answer : answers) {
            if (answer.getIsCorrect()) {
                return answer.getId().getAid();
            }
        }
        return null;
    }

    public Long getShowingTime() {
        Long wps = 60L;
        return statement.length() / wps * 60 * 1000;
    }
}
