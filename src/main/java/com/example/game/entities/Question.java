package com.example.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Id
    private String pid; //party id

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String statement;

    @Column
    private String category;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

}
