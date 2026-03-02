package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.exception.ResourceAlreadyExistsException;
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

    private Category getCategoryOrThrowException(String id, String accountId) {
        return categoryRepository.findByIdAndAccountId(id, accountId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("category_not_found", null, Locale.getDefault())));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO, String accountId) {
        categoryRepository.findByNameAndAccountId(categoryDTO.getName(), accountId).ifPresent(
                category -> {
                    throw new ResourceAlreadyExistsException("Category with name " + categoryDTO.getName() + " already exists");
                }
        );
        Category categoryToSave = categoryMapper.mapToCategory(categoryDTO);
        categoryToSave.setAccountId(accountId);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesByAccount(String accountId) {
        return categoryMapper.mapToListOfCategoriesDto(categoryRepository.findAllByAccountId(accountId));
    }

    @Override
    public CategoryDTO getCategoryByIdAndAccountId(String id, String accountId) {
        return categoryMapper.mapToCategoryDto(getCategoryOrThrowException(id, accountId));
    }

    @Override
    public void deleteCategoryByIdAndAccountId(String id, String accountId) {
        Category category = getCategoryOrThrowException(id, accountId);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO updateCategoryByIdAndAccountId(CategoryDTO categoryDTO, String id, String accountId) {

        categoryRepository.findByNameAndAccountId(categoryDTO.getName(), accountId).ifPresent(
                category -> {
                    throw new ResourceAlreadyExistsException("Category with name " + categoryDTO.getName() + " already exists");
                }
        );
        Category categoryToEdit = getCategoryOrThrowException(id, accountId);
        categoryToEdit.setName(categoryDTO.getName());
        categoryToEdit.setAccountId(accountId);
        Category savedCategory = categoryRepository.save(categoryToEdit);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public Page<CategoryDTO> getCategoriesByPageAndAccountId(Pageable pageable, String accountId) {
        Page<Category> categories = categoryRepository.findByAccountId(pageable, accountId);
        return categories.map(categoryMapper::mapToCategoryDto);
    }

    @Override
    public List<CategoryDTO> searchCategoriesByNameAndAccount(String name, String accountId) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCaseAndAccountId(name, accountId);
        return categoryMapper.mapToListOfCategoriesDto(categories);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.mapToListOfCategoriesDto(categoryRepository.findAll());
    }


}
