package com.dnd.eight.Domain.DailyQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select * from question q where q.question_id <= :questionId", nativeQuery = true)
    List<Question> findByRecentQuestionNumber(@Param("questionId") Long questionId);
}
