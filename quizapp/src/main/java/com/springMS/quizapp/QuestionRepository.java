package com.springMS.quizapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {


    List<Question> findByCategory(String category);

    @Query(value = "select * from question q where q.category = :category ORDER By Rand() LIMIT :numQ;",
            nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
