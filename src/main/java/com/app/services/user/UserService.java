package com.app.services.user;

import com.app.exceptions.UserFoundException;
import com.app.models.User;
import com.app.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user)  {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return userRepository.findByUsername(name);
    }


}
