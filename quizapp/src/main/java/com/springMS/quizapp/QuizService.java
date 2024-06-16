package com.springMS.quizapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public String createQuiz(String category, int numQ, String title) {
        List<Question> questionList = questionRepository.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questionList);
        quizRepository.save(quiz);
        return "quiz created";
    }

    public List<QuestionWrapper> getQuizQuestions(int id) {
         Optional<Quiz> quiz = quizRepository.findById(id);
         List<Question> questionListFromDB = quiz.get().getQuestionList();
         List<QuestionWrapper> questionsForUser = new ArrayList<>();
         for(Question q: questionListFromDB){
             QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestionTitle());
             questionsForUser.add(qw);
         }
         return questionsForUser;
    }

    public Integer calculateResult(int id, List<Response> responseList) {
        Quiz quiz = quizRepository.findById(id).get();
        List<Question> questionList = quiz.getQuestionList();
        int rightCount =0;
        for(Response response: responseList){
            getRightAnswer(questionList,response.getId());
            if(response.getResponse().equals(getRightAnswer(questionList,response.getId()))){
                rightCount++;
            }
        }
        return rightCount;
    }

    private String getRightAnswer(List<Question> questionList,int id) {
        return questionList.stream()
                .filter(question -> question.getId()==id)
                .map(question -> question.getRightAnswer())
                .findFirst().get();
    }
}
