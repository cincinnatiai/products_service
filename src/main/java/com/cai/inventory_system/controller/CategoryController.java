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
@RequestMapping("/api/inventory/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id,
                                                       @RequestHeader("x-account-id") String accountId){
        CategoryDTO categoryDTOFoundById = categoryService.getCategoryByIdAndAccountId(id, accountId);
        return new ResponseEntity<>(categoryDTOFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestHeader("x-account-id") String accountId){
        List<CategoryDTO> allCategories = categoryService.getAllCategoriesByAccount(accountId);
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO,
                                                      @RequestHeader("x-account-id") String accountId){
        CategoryDTO categoryCreated = categoryService.createCategory(categoryDTO, accountId);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable String id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("The Category was deleted successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryDTO categoryDTO,
                                                          @PathVariable String id,
                                                          @RequestHeader("x-account-id") String accountId){
        CategoryDTO categoryEdited = categoryService.updateCategoryByIdAndAccountId(categoryDTO, id, accountId);
        return new ResponseEntity<>(categoryEdited, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestHeader("x-account-id") String accountId)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<CategoryDTO> categories = categoryService.getCategoriesByPageAndAccountId(pageable, accountId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(@RequestParam String name,
                                                                    @RequestHeader("x-account-id") String accountId) {
        List<CategoryDTO> matchedCategories = categoryService.searchCategoriesByNameAndAccount(name, accountId);
        return new ResponseEntity<>(matchedCategories, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> allCategories() {
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);

    }
}
