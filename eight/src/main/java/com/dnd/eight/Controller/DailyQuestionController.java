package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.*;
import com.dnd.eight.Service.DailyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyQuestionController {
    private final DailyQuestionService questionService;

    //1. daily-question 오늘의 질문 조회
    @GetMapping("/daily-questions/space/{spaceId}")
    public DailyQuestionResponseDto todayQuestion(@PathVariable Long spaceId){
        return questionService.findTodayQuestion(spaceId);
    }

    //2. daily-question 오늘의 질문에 대한 user의 답변 저장
    @PostMapping("/daily-questions/{questionId}/answer/space/{spaceId}")
    public Long saveAnswer(@PathVariable Long questionId, @PathVariable Long spaceId, @RequestBody DailyQuestionAnswerRequestDto requestDto){
        return questionService.saveAnswer(questionId, spaceId, requestDto.getUserId(), requestDto.getContent());
    }

    //3. daily-question 오늘의 질문에 대한 답변 조회
    @GetMapping("/daily-questions/{questionId}/answers/space/{spaceId}")
    public List<DailyQuestionAnswersDto> getAnswers(@PathVariable Long questionId, @PathVariable Long spaceId){
        DailyQuestionAnswersDto responseDto = new DailyQuestionAnswersDto();

        List<DailyQuestionAnswersDto> answers = questionService.getAnswers(spaceId, questionId);
//        responseDto.setAnswers(answers);
        return answers;
    }

    //4. daily-question 통신기록 조회
    @GetMapping("/daily-questions/list/space/{spaceId}")
    public List<DailyQuestionListResponseDto> questionList(@PathVariable Long spaceId){
        return questionService.findQuestions(spaceId);
    }

    //5. daily-question 통신기록 댓글 저장
    @PostMapping("/daily-questions/{questionId}/comment")
    public Long saveComment(@PathVariable Long questionId, @RequestBody DailyQuestionCommentRequestDto requestDto){
        return questionService.saveComment(questionId, requestDto.getUserId(), requestDto.getContent(), requestDto.getEmoji());
    }

    //6. daily-question 통신기록중 하나의 기록 조회
    @GetMapping("/daily-questions/{questionId}/space/{spaceId}")
    public LinkedHashMap<String, Object> questionInfo(@PathVariable Long questionId, @PathVariable Long spaceId){
        return questionService.questionInfo(questionId, spaceId);
    }
}
