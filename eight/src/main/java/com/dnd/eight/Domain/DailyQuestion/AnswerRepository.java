package com.dnd.eight.Domain.DailyQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = "select a.content from answer a where a.question_id= :questionId and a.user_id= :userId", nativeQuery = true)
    String getAnswer(@Param("questionId") Long questionId, @Param("userId") Long userId);
}
