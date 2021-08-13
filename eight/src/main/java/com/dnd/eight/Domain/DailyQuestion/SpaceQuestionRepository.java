package com.dnd.eight.Domain.DailyQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpaceQuestionRepository extends JpaRepository<SpaceQuestion, Long> {
    @Query(value = "select * from space_question s where s.question_id= :questionId and s.space_id= :spaceId", nativeQuery = true)
    SpaceQuestion getSpaceQuestion(@Param("questionId") Long questionId, @Param("spaceId") Long spaceId);
}
