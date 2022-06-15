package com.sena.tecmiecommercebackend.controller;

import com.sena.tecmiecommercebackend.common.ApiResponse;
import com.sena.tecmiecommercebackend.repository.entity.Category;
import com.sena.tecmiecommercebackend.service.CategoryService;
import com.sena.tecmiecommercebackend.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Created the category."), HttpStatus.CREATED);
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Integer categoryId, @Valid @RequestBody Category category) {
        if (Helper.notNull(categoryService.readCategory(categoryId))) {
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse(true, "Category has been updated."), HttpStatus.OK);
        }
    categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(false, "Category does not exist."), HttpStatus.NOT_FOUND);
    }

}
