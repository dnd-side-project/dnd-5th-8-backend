package com.dnd.eight.Domain.DailyQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment c where c.question_id= :questionId and c.user_id= :userId", nativeQuery = true)
    Comment getComment(@Param("questionId") Long questionId, @Param("userId") Long userId);

}
