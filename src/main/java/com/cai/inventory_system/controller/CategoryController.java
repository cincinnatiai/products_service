package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@RequestParam String id){
        CategoryDTO categoryDTOFoundById = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDTOFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO categoryCreated = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategoryById(@RequestParam String id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryDTO categoryDTO, @RequestParam String id){
        CategoryDTO categoryEdited = categoryService.updateCategoryById(categoryDTO, id);
        return new ResponseEntity<>(categoryEdited, HttpStatus.CREATED);
    }
}
