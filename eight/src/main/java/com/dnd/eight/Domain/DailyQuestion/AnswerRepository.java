package com.dnd.eight.Domain.DailyQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = "select a.content from answer a where a.question_id= :questionId and a.user_id= :userId", nativeQuery = true)
    String getAnswer(@Param("questionId") Long questionId, @Param("userId") Long userId);

    @Query(value = "select count(*) from answer a where a.space_id= :spaceId and a.question_id= :questionId", nativeQuery = true) //space id, question id
    Long countAnswer(@Param("spaceId") Long spaceId, @Param("questionId") Long questionId);
}
