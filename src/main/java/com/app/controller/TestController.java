package com.app.controller;


import com.app.models.User;
import com.app.models.exam.Question;
import com.app.models.exam.Test;
import com.app.services.question.IQuestionService;
import com.app.services.test.ITestService;
import com.app.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ITestService testService;


    @PostMapping("")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions, Principal principal){
        User user = userService.findByUserName(principal.getName()).get();
        //hơi tù :D
        Double marksGot = 0.0;
        Integer correctAnswers = 0;
        Integer attempted = 0;
        double markSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();

        for (Question q:questions
        ) {
            Question question = questionService.findById(q.getId()).get();
            if (question.getAnswer().trim().equals(q.getGivenAnswer())) {
                correctAnswers+=1;
                marksGot+=markSingle;
            }
            if(q.getGivenAnswer()!=null&&!q.getGivenAnswer().equals("")){
                attempted+=1;
            }
        }

        Test test = new Test();
        test.setUser(user);
        test.setQuiz(questions.get(0).getQuiz());
        test.setAttempted(attempted);
        test.setCorrectAnswers(correctAnswers);
        test.setMarksGot(marksGot);
        testService.save(test);





        return new ResponseEntity<>( testService.save(test), HttpStatus.OK);
    }
}
