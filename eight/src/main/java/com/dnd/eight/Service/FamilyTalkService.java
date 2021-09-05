package com.dnd.eight.Service;

import com.dnd.eight.Domain.FamilyTalk.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FamilyTalkService {
    private final ParentCategoryRepository parentCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final FamilyTalkQuestionRepository familyTalkQuestionRepository;

    // REST API: 하위 카테고리 생성
    @Transactional
    public Long createSubCategory(Long parentCategoryId, String content) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + parentCategoryId));

        return subCategoryRepository.save(SubCategory.createSubCategory(content, parentCategory)).getId();
    }

    // REST API: 질문 생성
    @Transactional
    public Long createQuestions(Long subCategoryId, String content){
        SubCategory subCategory =  subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + subCategoryId));

        return familyTalkQuestionRepository
                .save(FamilyTalkQuestion.createFamilyTalkQuestions(content, subCategory)).getId();
    }

    // REST API: 모든 카테고리 찾기
    @Transactional(readOnly = true)
    public List<ParentCategory> getCategories(){
        return parentCategoryRepository.findAll();
    }

    // Web Socket: 선택된 하위 카테고리에 대한 질문들 랜덤으로 반환
    public List<String> getQuestions(List<Long> categoryIdList, Integer roundNumber) {
        List<String> questions = new ArrayList<>();
        int countCategory = categoryIdList.size();
        Collections.shuffle(categoryIdList);

        for(int i = 0; i<roundNumber; ++i){
            Long subCategoryId = categoryIdList.get(i % countCategory);
            SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(()-> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + subCategoryId));
            questions.add(getRandomQuestion(subCategory.getFamilyTalkQuestions()));
        }

        return questions;
    }

    public String getRandomQuestion(List<FamilyTalkQuestion> questions){
        Random rand = new Random();
        int questionsSize = questions.size();
        int index = rand.nextInt(questionsSize);
        return questions.get(index).getContent();
    }
}
