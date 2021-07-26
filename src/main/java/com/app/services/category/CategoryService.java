package com.app.services.category;

import com.app.exceptions.UserFoundException;
import com.app.models.exam.Category;
import com.app.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements  ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    public CategoryService() {
        super();
    }

    @Override
    public Iterable<Category> findAll() {

        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {

        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category)  {

        return categoryRepository.save(category);
    }

}
