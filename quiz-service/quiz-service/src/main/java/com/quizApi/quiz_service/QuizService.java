package com.quizApi.quiz_service;

import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizInterface quizInterface;

    public String createQuiz(String category, int numQ, String title) {
       List<Integer> questionList = quizInterface.getQuestionsFromQuiz(category,numQ).getBody();
       Quiz quiz = new Quiz();
       quiz.setTitle(title);
       quiz.setQuestionIds(questionList);
       quizRepository.save(quiz);
        return "quiz created";
    }

    public  ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
         List<Integer> questionList = quiz.get().getQuestionIds();
         ResponseEntity<List<QuestionWrapper>> questionsForUser =  quizInterface.getQuestions(questionList);
        return questionsForUser;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responseList) {
        Integer count = quizInterface.getScore(responseList);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    private String getRightAnswer(List<TypePatternQuestions.Question> questionList, int id) {
//        return questionList.stream()
//                .filter(question -> question.getId()==id)
//                .map(question -> question.getRightAnswer())
//                .findFirst().get();
        return null;
    }
}
