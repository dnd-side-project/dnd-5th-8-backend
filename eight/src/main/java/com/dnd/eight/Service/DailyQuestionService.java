package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.DailyQuestionResponseDto;
import com.dnd.eight.Domain.DailyQuestion.*;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import com.dnd.eight.Domain.Space.Space;
import com.dnd.eight.Domain.Space.SpaceRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public String findTodayQuestion(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        Question question = questionRepository.findById(space.getQuestion_number())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + space.getQuestion_number()));

        return question.getContent();
    }

    @Transactional
    public Long saveAnswer(Long questionId, Long userId, String answerContent) {
        Question question = questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + questionId));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        Answer answer = Answer.createAnswer(question, user, answerContent);
        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    public List<Question> findQuestions(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        Long recent_question_number = space.getQuestion_number()-1;
        return questionRepository.findByRecentQuestionNumber(recent_question_number);
    }


    @Transactional
    public Long saveComment(Long questionId, Long userId, String content) {
        Question question = questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + questionId));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        Comment comment = Comment.createComment(question, user, content);
        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<String, List<DailyQuestionResponseDto>> questionInfo(Long questionId, Long spaceId) {
        DailyQuestionResponseDto responseDto;
        List<DailyQuestionResponseDto> answerList = new ArrayList<>();
        List<DailyQuestionResponseDto> commentList = new ArrayList<>();
        LinkedHashMap<String, List<DailyQuestionResponseDto>> map = new LinkedHashMap<>();

        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        List<User> users = space.getUsers();
        for(User user: users){
            responseDto = new DailyQuestionResponseDto();
            responseDto.setUser_nickName(user.getNickname());
            responseDto.setContent(answerRepository.getAnswer(questionId, user.getId()));
            answerList.add(responseDto);

            responseDto = new DailyQuestionResponseDto();
            responseDto.setUser_nickName(user.getNickname());
            responseDto.setContent(commentRepository.getComment(questionId, user.getId()));
            commentList.add(responseDto);
        }

        map.put("answer", answerList);
        map.put("comment", commentList);

        return map;
    }
}
