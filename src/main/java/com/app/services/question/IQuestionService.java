package com.app.services.question;

import com.app.models.exam.Question;
import com.app.models.exam.Quiz;
import com.app.services.IService;

public interface IQuestionService extends IService<Question> {
    Iterable<Question> getQuestionsByQuiz(Quiz quiz);
}
