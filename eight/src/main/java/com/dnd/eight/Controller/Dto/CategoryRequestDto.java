package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequestDto {
    private Long parentCategoryId;
    private String content;
}
