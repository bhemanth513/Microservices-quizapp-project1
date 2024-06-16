package com.springMS.questionService.service;

import com.springMS.questionService.model.Question;
import com.springMS.questionService.model.QuestionWrapper;
import com.springMS.questionService.model.Response;
import com.springMS.questionService.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    public List<Question> getByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Integer> getQuestionsFromQuiz(String categoryName, Integer numOfQuestions) {
        List<Integer> questionList = questionRepository.findRandomQuestionsByCategory(categoryName,numOfQuestions);
        return questionList;
    }

    public List<QuestionWrapper> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        List<Question> questions = new ArrayList<>();
        for(Integer id: questionIds){
            questions.add(questionRepository.findById(id).get());
        }
        for(Question q: questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(q.getId());
            wrapper.setQuestionTitle(q.getQuestionTitle());
            wrapper.setOption1(q.getOption1());
            wrapper.setOption2(q.getOption2());
            wrapper.setOption3(q.getOption3());
            wrapper.setOption4(q.getOption4());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    public Integer getScore(List<Response> responseList) {
        int rightCount = 0;
        for(Response response: responseList){
            Question question = questionRepository.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                rightCount++;
            }
        }
        return rightCount;
    }
}
