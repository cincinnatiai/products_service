package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import com.cai.inventory_system.entity.AccountCategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountCategoryMapper {

    public AccountCategoryDTO mapToAccountCategoryDto(AccountCategoryEntity accountCategoryEntity){
        return new AccountCategoryDTO(
                accountCategoryEntity.getId(),
                accountCategoryEntity.getName(),
                accountCategoryEntity.getCreatedAt(),
                accountCategoryEntity.getUpdatedAt(),
                accountCategoryEntity.getCreatedBy(),
                accountCategoryEntity.getAccountId()
        );
    }

    public AccountCategoryEntity mapToAccountCategory(AccountCategoryDTO accountCategoryDTO){
        return new AccountCategoryEntity(
                accountCategoryDTO.getId(),
                accountCategoryDTO.getName(),
                accountCategoryDTO.getCreatedAt(),
                accountCategoryDTO.getUpdatedAt(),
                accountCategoryDTO.getCreatedBy(),
                accountCategoryDTO.getAccountId()
        );
    }

    public List<AccountCategoryDTO> mapToListOfAccountCategoriesDto(List<AccountCategoryEntity> listOfAccountCategories){
        return listOfAccountCategories.stream().map(this::mapToAccountCategoryDto)
                .collect(Collectors.toList());
    }
}
