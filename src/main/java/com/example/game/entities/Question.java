package com.example.game.entities;

import com.example.game.dto.OriginalQuestionDto;
import com.example.game.entities.key.AnswerKey;
import com.example.game.entities.key.QuestionKey;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gquestion")
@IdClass(QuestionKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Id
    private Long qid;

    @Getter
    @Column
    private String statement;

    @Getter
    @Column
    private Long time;

    @Getter
    @Setter
    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @Id
    private Game game;

    public Question(OriginalQuestionDto originalQuestionDto, Game game) {
        this.qid = (long) originalQuestionDto.index;
        this.statement = originalQuestionDto.question;
        this.time = (long) originalQuestionDto.time_limit;
        this.game = game;
        this.answers = new java.util.ArrayList<>();
        for (int i = 0; i < originalQuestionDto.answers.size(); i++) {
            Answer answer = new Answer(originalQuestionDto.answers.get(i), this);
            this.answers.add(answer);
        }
    }

    public Long getCorrectAnswer() {
        for (Answer answer : answers) {
            if (answer.getIsCorrect()) {
                return answer.getAid();
            }
        }
        return null;
    }

    public QuestionKey getKey() {
        return new QuestionKey(qid, game);
    }

    public Question takeSnapshot() {
        Question snapshot = new Question();
        snapshot.qid = this.qid;
        snapshot.statement = this.statement;
        snapshot.time = this.time;
        snapshot.game = this.game;
        snapshot.answers = new java.util.ArrayList<>();
        for (Answer answer : this.answers) {
            snapshot.answers.add(answer.takeSnapshot());
        }
        return snapshot;
    }

    public void recovery(Question snapshot) {
        this.qid = snapshot.qid;
        this.statement = snapshot.statement;
        this.time = snapshot.time;
        this.answers = new java.util.ArrayList<>();
        this.game = snapshot.game;
        for (Answer answer : snapshot.answers) {
            Answer a = new Answer();
            a.recovery(answer);
            this.answers.add(a);
        }
    }
}
