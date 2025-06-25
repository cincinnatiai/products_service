package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO mapToCategoryDto(Category category){
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getCreated_at(),
                category.getUpdated_at()
        );
    }

    public Category mapToCategory(CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                categoryDTO.getCreated_at(),
                categoryDTO.getUpdated_at()
        );
    }
}
