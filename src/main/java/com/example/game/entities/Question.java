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

    @Column
    private String statement;

    @Column
    private String category;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

}
