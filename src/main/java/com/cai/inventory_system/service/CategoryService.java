package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(String id);
    void deleteCategoryById(String id);
    CategoryDTO updateCategoryById(CategoryDTO categoryDTO, String id);
}
