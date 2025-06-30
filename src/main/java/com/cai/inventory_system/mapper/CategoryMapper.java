package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryDTO> mapToListOfCategoriesDto(List<Category> listOfCate){
        return listOfCate.stream().map(this::mapToCategoryDto)
                .collect(Collectors.toList());
    }
}
