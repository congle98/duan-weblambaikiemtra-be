package com.app.services.quiz;


import com.app.models.exam.Category;
import com.app.models.exam.Quiz;
import com.app.services.IService;

public interface IQuizService extends IService<Quiz> {
    Iterable<Quiz> findAllByCategory(Category category);
    Iterable<Quiz> findAllByCategoryAndActive(Category category);
    Iterable<Quiz> findAllQuizzesActive();

}
