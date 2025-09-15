package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id){
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
    public ResponseEntity<String> deleteCategoryById(@PathVariable String id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryDTO categoryDTO, @PathVariable String id){
        CategoryDTO categoryEdited = categoryService.updateCategoryById(categoryDTO, id);
        return new ResponseEntity<>(categoryEdited, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<CategoryDTO> categories = categoryService.getCategoriesByPage(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CategoryDTO>> createBaseCategories(@RequestBody List<CategoryDTO> categoryDTOS){
        List<CategoryDTO> createdCategories = categoryDTOS.stream().map(categoryService:: createCategory).toList();
        return new ResponseEntity<>(createdCategories, HttpStatus.CREATED);
    }




}
