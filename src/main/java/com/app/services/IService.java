package com.app.services;


import com.app.exceptions.UserFoundException;

import java.util.Optional;

public interface IService <T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    void  delete(Long id);
    T save(T t);


}
