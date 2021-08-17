package com.dnd.eight.Service;

import com.dnd.eight.Domain.FamilyTalk.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyTalkService {
    private final ParentCategoryRepository parentCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final FamilyTalkQuestionRepository familyTalkQuestionRepository;

    @Transactional(readOnly = true)
    public List<ParentCategory> getCategories(){
        return parentCategoryRepository.findAll();
    }

    public Long createSubCategory(Long parentCategoryId, String content) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + parentCategoryId));

        return subCategoryRepository.save(SubCategory.createSubCategory(content, parentCategory)).getId();
    }
}
