package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class DailyQuestionAnswerRequestDto {
    String content;
    Long userId;
}
