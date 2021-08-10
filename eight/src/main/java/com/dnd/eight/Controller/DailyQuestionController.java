package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.DailyQuestionRequestDto;
import com.dnd.eight.Controller.Dto.DailyQuestionResponseDto;
import com.dnd.eight.Domain.DailyQuestion.Question;
import com.dnd.eight.Service.DailyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyQuestionController {
    /** 추가 할 사항
     * 1. space의 question_number 올리는 조건
     * 2. 댓글 생성된 시간 (AOP 적용)
     * 3. REST API 규칙에 맞게 모든 controller의 반환 (ex. http status code, POST => created 201) 변경
     */
    private final DailyQuestionService questionService;

    // 오늘 질문 Get mapping
    @GetMapping("/daily-questions/space/{spaceId}")
    public String todayQuestion(@PathVariable Long spaceId){
        return questionService.findTodayQuestion(spaceId);
    }

    // 오늘 질문에 대한 답 저장 (answer) Post mapping
    @PostMapping("/daily-questions/{questionId}/answer")
    public Long saveAnswer(@PathVariable Long questionId, @RequestBody DailyQuestionRequestDto requestDto){
        return questionService.saveAnswer(questionId, requestDto.getUserId(), requestDto.getContent());
    }

    // 통신기록
    // 1. 오늘날 전까지의 질문 리스트, 각 질문들에 대한 comment수 같이 retrurn => Get mapping
    @GetMapping("/daily-questions/list/space/{spaceId}")
    public List<Question> questionList(@PathVariable Long spaceId){
        return questionService.findQuestions(spaceId);
    }

    // 2. 이전날의 질문에 대한 comment 저장 => Post mapping
    @PostMapping("/daily-questions/{questionId}/comment")
    public Long saveComment(@PathVariable Long questionId, @RequestBody DailyQuestionRequestDto requestDto){
        return questionService.saveComment(questionId, requestDto.getUserId(), requestDto.getContent());
    }

    // 3. 이전날의 질문을 클릭하면 해당 질문에 대한 answer, comment return => Get mapping
    @GetMapping("/daily-questions/{questionId}/space/{spaceId}")
    public LinkedHashMap<String, List<DailyQuestionResponseDto>> questionInfo(@PathVariable Long questionId, @PathVariable Long spaceId){
        return questionService.questionInfo(questionId, spaceId);
    }
}
