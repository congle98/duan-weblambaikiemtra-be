package com.app.repositories;

import com.app.models.User;
import com.app.models.exam.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITestRepository extends JpaRepository<Test,Long> {
    List<Test> findByUser (User user);
    // là sẽ lấy ra được hết bài test của thằng user
}
