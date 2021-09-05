package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyTalkQuestionRequestDto {
    private Long subCategoryId;
    private String content;
}
