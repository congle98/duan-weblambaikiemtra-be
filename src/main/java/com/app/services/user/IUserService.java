package com.app.services.user;

import com.app.models.User;
import com.app.services.IService;

import java.util.Optional;

public interface IUserService extends IService<User> {
    Optional<User> findByUserName(String name);
}
