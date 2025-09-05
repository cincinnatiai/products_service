package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.CategoryMapper;
import com.cai.inventory_system.repository.CategoryRepository;
import com.cai.inventory_system.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    private Category getCategoryOrThrowException(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("product_not_found", null, Locale.getDefault())));
    }

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
        return categoryMapper.mapToCategoryDto(getCategoryOrThrowException(id));
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO, String id) {
        Category categoryToEdit = getCategoryOrThrowException(id);
        categoryToEdit.setName(categoryDTO.getName());
        categoryToEdit.setCreated_at(categoryDTO.getCreated_at());
        categoryToEdit.setUpdated_at(categoryDTO.getUpdated_at());
        Category savedCategory = categoryRepository.save(categoryToEdit);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public Page<CategoryDTO> getCategoriesByPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::mapToCategoryDto);
    }

    @Override
    public Page<CategoryDTO> getCategoriesByPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::mapToCategoryDto);
    }
}
