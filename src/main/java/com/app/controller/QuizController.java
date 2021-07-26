package com.app.controller;

import com.app.models.exam.Category;
import com.app.models.exam.Quiz;
import com.app.repositories.IQuizRepository;
import com.app.services.quiz.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {
    @Autowired
    private IQuizService quizService;

    @PostMapping("")
    public ResponseEntity<?> addQuiz(@RequestBody Quiz quiz){
        return  new ResponseEntity<>(quizService.save(quiz),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getQuiz(@PathVariable Long id){
        System.out.println("đây là quiz của tao"+quizService.findById(id).get());
        return new ResponseEntity<>(quizService.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllQuiz(){
        return new ResponseEntity<>(quizService.findAll(),HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz){
        return new ResponseEntity<>(quizService.save(quiz),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id){
        quizService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category/{cateId}")
    public ResponseEntity<?> getQuizzesOfCategory(@PathVariable Long cateId){
        Category category = new Category();
        category.setId(cateId);
        return  new ResponseEntity<>(quizService.findAllByCategory(category),HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllQuizzesActive(){
        return  new ResponseEntity<>(quizService.findAllQuizzesActive(),HttpStatus.OK);
    }

    @GetMapping("/category/active/{cateId}")
    public ResponseEntity<?> getAllQuizzesActiveOfCategory(@PathVariable Long cateId){
        Category category = new Category();
        category.setId(cateId);
        return  new ResponseEntity<>(quizService.findAllByCategoryAndActive(category),HttpStatus.OK);
    }
}
