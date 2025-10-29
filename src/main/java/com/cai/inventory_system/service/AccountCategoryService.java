package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountCategoryService {
    AccountCategoryDTO createAccountCategory(AccountCategoryDTO accountCategoryDTO);
    List<AccountCategoryDTO> getAllAccountCategories();
    AccountCategoryDTO getAccountCategoryById(String id);
    void deleteAccountCategoryById(String id);
    AccountCategoryDTO updateAccountCategoryById(AccountCategoryDTO accountCategoryDTO, String id);
    Page<AccountCategoryDTO> getAccountCategoriesByPage(Pageable pageable);
    List<AccountCategoryDTO> searchAccountCategoriesByName(String name);
    List<AccountCategoryDTO> getAccountCategoriesByAccountId(String accountId);
}
