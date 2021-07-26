package com.app.services.test;

import com.app.models.exam.Test;
import com.app.repositories.ITestRepository;
import com.app.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService implements ITestService{
    @Autowired
    private ITestRepository testRepository;

    @Override
    public Iterable<Test> findAll() {
        return null;
    }

    @Override
    public Optional<Test> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }
}
