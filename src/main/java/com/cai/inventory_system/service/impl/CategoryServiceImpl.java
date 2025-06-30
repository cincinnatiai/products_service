package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.CategoryMapper;
import com.cai.inventory_system.repository.CategoryRepository;
import com.cai.inventory_system.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave = categoryMapper.mapToCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.mapToListOfCategoriesDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getCategoryById(String id) {
        return categoryMapper.mapToCategoryDto(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category Id not found")));
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO, String id) {
        Category categoryToEdit = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category Id not found"));
        categoryToEdit.setName(categoryDTO.getName());
        categoryToEdit.setCreated_at(categoryDTO.getCreated_at());
        categoryToEdit.setUpdated_at(categoryDTO.getUpdated_at());
        return categoryMapper.mapToCategoryDto(categoryToEdit);
    }
}
