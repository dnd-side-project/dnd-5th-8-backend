package com.dnd.eight.Domain.DailyQuestion;

import com.dnd.eight.Domain.BaseTimeEntity;
import com.dnd.eight.Domain.Space.Space;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "space_question")
@Getter
@NoArgsConstructor
public class SpaceQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public void setSpace(Space space){
        this.space = space;
        space.getSpaceQuestionList().add(this);
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    public static SpaceQuestion createSpaceQuestion(Question question, Space space){
        SpaceQuestion spaceQuestion = new SpaceQuestion();
        spaceQuestion.setQuestion(question);
        spaceQuestion.setSpace(space);

        return spaceQuestion;
    }
}
