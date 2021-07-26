package com.app.controller;

import com.app.models.exam.Category;
import com.app.services.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestBody Category category){

        return  new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
