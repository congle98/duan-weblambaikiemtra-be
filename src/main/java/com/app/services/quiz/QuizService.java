package com.app.services.quiz;

import com.app.models.exam.Category;
import com.app.models.exam.Quiz;

import com.app.repositories.IQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService implements IQuizService {
    @Autowired
    private IQuizRepository quizRepository;
    @Override
    public Iterable<Quiz> findAll() {

        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findById(Long id) {

        return quizRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {

        return quizRepository.save(quiz);
    }

    @Override
    public Iterable<Quiz> findAllByCategory(Category category) {
        return quizRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Quiz> findAllByCategoryAndActive(Category category) {
        return quizRepository.findByCategoryAndActive(category,true);
    }

    @Override
    public Iterable<Quiz> findAllQuizzesActive() {
        return quizRepository.findByActive(true);
    }
}
