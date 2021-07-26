package com.app.repositories;

import com.app.models.exam.Category;
import com.app.models.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz,Long> {
    Iterable<Quiz> findAllByCategory(Category category);
    Iterable<Quiz> findByActive(Boolean b);
    Iterable<Quiz> findByCategoryAndActive(Category category, Boolean b);
}
