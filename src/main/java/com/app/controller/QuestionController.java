package com.app.controller;

import com.app.models.exam.Question;
import com.app.models.exam.Quiz;
import com.app.models.exam.Test;
import com.app.services.question.IQuestionService;
import com.app.services.quiz.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequestMapping("/question")
@CrossOrigin("*")
@RestController
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuizService quizService;



    @PostMapping("")
    public ResponseEntity<?> addQuestion(@RequestBody Question question){
        return  new ResponseEntity<>(questionService.save(question), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getQuestion(@PathVariable Long id){
        return new ResponseEntity<>(questionService.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getQuestion(){
        return new ResponseEntity<>(questionService.findAll(),HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question){
        return new ResponseEntity<>(questionService.save(question),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        questionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionsOfQuizForTest(@PathVariable Long quizId){
//        Quiz quiz = new Quiz();
//        quiz.setId(quizId);
//        return new ResponseEntity<>(questionService.getQuestionsByQuiz(quiz),HttpStatus.OK);
        // lấy luôn questions từ quiz và kiểm tra độ dài maxquiz rồi trả về sao cho đúng ngần ý câu
      Quiz quiz =   quizService.findById(quizId).get();
        List<Question> questions = quiz.getQuestions();
        if(questions.size()>Integer.parseInt(quiz.getNumberOfQuestion())){
                questions = questions.subList(0,Integer.parseInt(quiz.getNumberOfQuestion()));
        }
        //trộn câu hỏi
        Collections.shuffle(questions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    @GetMapping("/quiz/all/{quizId}")
    public ResponseEntity<?> getAllQuestionsOfAdmin(@PathVariable Long quizId){
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        List<Question> questions = (List<Question>) questionService.getQuestionsByQuiz(quiz);

        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    //evalQuiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?>evalQuiz(@RequestBody List<Question> questions){

        //tù vl. ko ai làm ntn cả
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
        Map<String, Object> of = new HashMap<>();
           of.put("marksGot",marksGot);
           of.put("correctAnswers",correctAnswers);
           of.put("attempted",attempted);


        return new ResponseEntity<>(of,HttpStatus.OK);
    }




}
