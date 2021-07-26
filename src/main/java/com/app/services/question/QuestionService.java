package com.app.services.question;


import com.app.models.exam.Question;
import com.app.models.exam.Quiz;
import com.app.repositories.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;
    @Override
    public Iterable<Question> findAll() {

        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findById(Long id) {

        return questionRepository.findById(id);
    }

    @Override
    public Iterable<Question> getQuestionsByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Question save(Question question) {

        return questionRepository.save(question);
    }



}
