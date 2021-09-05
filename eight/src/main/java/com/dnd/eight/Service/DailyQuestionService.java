package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.*;
import com.dnd.eight.Domain.DailyQuestion.*;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import com.dnd.eight.Domain.Space.Space;
import com.dnd.eight.Domain.Space.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyQuestionService {

    private final SpaceRepository spaceRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final SpaceQuestionRepository spaceQuestionRepository;
    private final UserService userService;

    @Transactional
    @Scheduled(cron = "0 0 09 * * *", zone = "Asia/Seoul")
    public void updateQuestion(){
        SpaceQuestion spaceQuestion;
        List<Space> spaces = spaceRepository.findAll();
        for(Space space: spaces){
            Long questionId = space.updateQuestionNumber();
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + questionId));
            spaceQuestion = SpaceQuestion.createSpaceQuestion(question, space);
            spaceQuestionRepository.save(spaceQuestion);
        }
    }

    @Transactional(readOnly = true)
    public DailyQuestionResponseDto findTodayQuestion(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        Long questionId = space.getQuestion_number();
        SpaceQuestion spaceQuestion = spaceQuestionRepository.getSpaceQuestion(questionId, spaceId);
        Question question = spaceQuestion.getQuestion();

        DailyQuestionResponseDto responseDto = new DailyQuestionResponseDto();
        responseDto.setQuestionId(question.getId());
        responseDto.setQuestionContent(question.getContent());
        responseDto.setDate(spaceQuestion.getCreatedDate());
        return responseDto;
    }

    @Transactional(readOnly = true)
    public DailyQuestionResponseDto findQuestion(Long spaceId, Long questionId) {
        SpaceQuestion spaceQuestion = spaceQuestionRepository.getSpaceQuestion(questionId, spaceId);
        Question question = spaceQuestion.getQuestion();

        DailyQuestionResponseDto responseDto = new DailyQuestionResponseDto();
        responseDto.setQuestionId(question.getId());
        responseDto.setQuestionContent(question.getContent());
        responseDto.setDate(spaceQuestion.getCreatedDate());
        return responseDto;
    }

    @Transactional
    public Long saveAnswer(Long questionId, Long spaceId, Long userId, String answerContent) {
        Question question = questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + questionId));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        Answer answer = Answer.createAnswer(spaceId, question, user, answerContent);
        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    public List<DailyQuestionAnswersDto> getAnswers(Long spaceId, Long questionId){
        List<Answer> answers = answerRepository.getAnswers(spaceId, questionId);
        List<DailyQuestionAnswersDto>  answerList = new ArrayList<>();
        DailyQuestionAnswersDto dto;

        for(Answer answer : answers){
            dto = new DailyQuestionAnswersDto();
            dto.setNickName(userService.findById(answer.getId()).getNickname());
            dto.setAnswer(answer.getContent());
            answerList.add(dto);
        }

        return answerList;
    }

    @Transactional(readOnly = true)
    public List<DailyQuestionListResponseDto> findQuestions(Long spaceId) {
        List<DailyQuestionListResponseDto> responseDtoList = new ArrayList<>();
        DailyQuestionListResponseDto responseDto;
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        Long recent_question_number = space.getQuestion_number()-1;
        List<Question> questions = questionRepository.findByRecentQuestionNumber(recent_question_number);

        for(Question q : questions){
            SpaceQuestion spaceQuestion = spaceQuestionRepository.getSpaceQuestion(q.getId(), spaceId);
            responseDto = new DailyQuestionListResponseDto();
            responseDto.setQuestionId(q.getId());
            responseDto.setContent(q.getContent());
            responseDto.setAnswerCount(answerRepository.countAnswer(space.getId(), q.getId()));
            responseDto.setDate(spaceQuestion.getCreatedDate());
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional
    public Long saveComment(Long questionId, Long userId, String content, String emoji) {
        Question question = questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + questionId));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        Comment comment = Comment.createComment(question, user, content, emoji);
        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<String, Object> questionInfo(Long questionId, Long spaceId) {
        DailyQuestionResponseDto questionResponseDto = this.findQuestion(spaceId, questionId);
        AnswerResponseDto answerResponseDto;
        CommentResponseDto commentResponseDto;

        List<Object> answerList = new ArrayList<>();
        List<Object> commentList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        List<User> users = space.getUsers();
        for(User user: users){
            answerResponseDto = new AnswerResponseDto();
            answerResponseDto.setUser_profile(user.getProfile());
            answerResponseDto.setUser_nickName(user.getNickname());
            answerResponseDto.setContent(answerRepository.getAnswer(questionId, user.getId()));
            answerList.add(answerResponseDto);

            Comment comment = commentRepository.getComment(questionId, user.getId());
            if(comment == null){
                continue;
            }
            commentResponseDto = new CommentResponseDto();
            commentResponseDto.setUser_profile(user.getProfile());
            commentResponseDto.setUser_nickName(user.getNickname());
            commentResponseDto.setContent(comment.getContent());
            commentResponseDto.setCreatedDate(comment.getCreatedDate());
            commentResponseDto.setEmoji(comment.getEmoji());
            commentList.add(commentResponseDto);
        }
        map.put("question", questionResponseDto);
        map.put("answer", answerList);
        map.put("comment", commentList);

        return map;
    }
}
