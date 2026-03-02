package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO, String accountId);
    List<CategoryDTO> getAllCategoriesByAccount(String accountId);
    CategoryDTO getCategoryByIdAndAccountId(String id, String accountId);
    void deleteCategoryByIdAndAccountId(String id,  String accountId);
    CategoryDTO updateCategoryByIdAndAccountId(CategoryDTO categoryDTO, String id, String accountId);
    Page<CategoryDTO> getCategoriesByPageAndAccountId(Pageable pageable, String accountId);
    List<CategoryDTO> searchCategoriesByNameAndAccount(String name, String accountId);
    List<CategoryDTO> getAllCategories();

}
