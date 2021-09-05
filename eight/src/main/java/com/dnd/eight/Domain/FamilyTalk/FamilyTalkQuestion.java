package com.dnd.eight.Domain.FamilyTalk;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="familyTalk_question")
@Getter
@NoArgsConstructor
public class FamilyTalkQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="familytalk_question_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sub_category_id")
    private SubCategory subCategory;

    public void setSubCategory(SubCategory subCategory){
        this.subCategory = subCategory;
        subCategory.getFamilyTalkQuestions().add(this);
    }

    public static FamilyTalkQuestion createFamilyTalkQuestions(String content, SubCategory subCategory){
        FamilyTalkQuestion familyTalkQuestion = new FamilyTalkQuestion();

        familyTalkQuestion.content = content;
        familyTalkQuestion.setSubCategory(subCategory);

        return familyTalkQuestion;
    }
}

