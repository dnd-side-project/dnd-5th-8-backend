package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DailyQuestionListResponseDto {
    private Long questionId;
    private String content;
    private Long answerCount;
    private LocalDateTime date;
}
