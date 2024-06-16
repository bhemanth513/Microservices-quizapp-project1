package com.springMS.questionService.controller;

import com.springMS.questionService.model.Question;
import com.springMS.questionService.model.QuestionWrapper;
import com.springMS.questionService.model.Response;
import com.springMS.questionService.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getQuestions(){
        try{
            return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category){
        try{
            return new ResponseEntity<>(questionService.getByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        try {
            questionService.addQuestion(question);
            return new ResponseEntity<>("Question Added Successfully",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
    }

    //generate questions
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsFromQuiz
    (@RequestParam String categoryName, @RequestParam Integer numOfQuestions){
        List<Integer> questions = questionService.getQuestionsFromQuiz(categoryName,numOfQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }


    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questions){
        System.out.println(environment.getProperty("local.server.port"));
        List<QuestionWrapper> wrappers = questionService.getQuestionsFromIds(questions);
        return new ResponseEntity<>(wrappers,HttpStatus.CREATED);
    }

    //getScore
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responseList){
        Integer count = questionService.getScore(responseList);
        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
