package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DailyQuestionResponseDto {
    private Long questionId;
    private String questionContent;
    private LocalDateTime date;
}
