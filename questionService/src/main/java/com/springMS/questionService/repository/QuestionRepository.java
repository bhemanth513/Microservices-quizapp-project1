package com.springMS.questionService.repository;

import com.springMS.questionService.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {


    List<Question> findByCategory(String category);

    @Query(value = "select q.id from question q where q.category = :category ORDER By Rand() LIMIT :numQ;",
            nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
