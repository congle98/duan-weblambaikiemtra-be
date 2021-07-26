package com.app.repositories;

import com.app.models.exam.Question;
import com.app.models.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends JpaRepository<Question,Long> {
    Iterable<Question> findAllByQuiz(Quiz quiz);
}
