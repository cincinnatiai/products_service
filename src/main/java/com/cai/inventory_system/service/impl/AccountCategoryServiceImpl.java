package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import com.cai.inventory_system.entity.AccountCategoryEntity;
import com.cai.inventory_system.exception.ResourceAlreadyExistsException;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.AccountCategoryMapper;
import com.cai.inventory_system.repository.AccountCategoryRepository;
import com.cai.inventory_system.service.AccountCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class AccountCategoryServiceImpl implements AccountCategoryService {

    private final AccountCategoryMapper accountCategoryMapper;
    private final AccountCategoryRepository accountCategoryRepository;
    private final MessageSource messageSource;

    private AccountCategoryEntity getAccountCategoryOrThrowException(String id) {
        return accountCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("product_not_found", null, Locale.getDefault())));
    }

    @Override
    public AccountCategoryDTO createAccountCategory(AccountCategoryDTO accountCategoryDTO) {
        accountCategoryRepository.findByName(accountCategoryDTO.getName()).ifPresent(
                accountCategory -> {
                    throw new ResourceAlreadyExistsException("AccountCategory with name " + accountCategoryDTO.getName() + " already exists");
                }
        );
        final AccountCategoryEntity accountCategoryToSave = accountCategoryMapper.mapToAccountCategory(accountCategoryDTO);
        AccountCategoryEntity savedAccountCategory = accountCategoryRepository.save(accountCategoryToSave);
        return accountCategoryMapper.mapToAccountCategoryDto(savedAccountCategory);
    }

    @Override
    public List<AccountCategoryDTO> getAllAccountCategories() {
        return accountCategoryMapper.mapToListOfAccountCategoriesDto(accountCategoryRepository.findAll());
    }

    @Override
    public AccountCategoryDTO getAccountCategoryById(String id) {
        return accountCategoryMapper.mapToAccountCategoryDto(getAccountCategoryOrThrowException(id));
    }

    @Override
    public void deleteAccountCategoryById(String id) {
        accountCategoryRepository.deleteById(id);
    }

    @Override
    public AccountCategoryDTO updateAccountCategoryById(AccountCategoryDTO accountCategoryDTO, String id) {
        accountCategoryRepository.findByName(accountCategoryDTO.getName()).ifPresent(
                accountCategory -> {
                    throw new ResourceAlreadyExistsException("AccountCategory with name " + accountCategoryDTO.getName() + " already exists");
                }
        );
        AccountCategoryEntity accountCategoryToEdit = getAccountCategoryOrThrowException(id);
        accountCategoryToEdit.setName(accountCategoryDTO.getName());
        accountCategoryToEdit.setCreatedAt(accountCategoryDTO.getCreatedAt());
        accountCategoryToEdit.setUpdatedAt(accountCategoryDTO.getUpdatedAt());
        accountCategoryToEdit.setCreatedBy(accountCategoryDTO.getCreatedBy());
        accountCategoryToEdit.setAccountId(accountCategoryDTO.getAccountId());
        AccountCategoryEntity savedAccountCategory = accountCategoryRepository.save(accountCategoryToEdit);
        return accountCategoryMapper.mapToAccountCategoryDto(savedAccountCategory);
    }

    @Override
    public Page<AccountCategoryDTO> getAccountCategoriesByPage(Pageable pageable) {
        Page<AccountCategoryEntity> accountCategories = accountCategoryRepository.findAll(pageable);
        return accountCategories.map(accountCategoryMapper::mapToAccountCategoryDto);
    }

    @Override
    public List<AccountCategoryDTO> searchAccountCategoriesByName(String name) {
        List<AccountCategoryEntity> accountCategories = accountCategoryRepository.findByNameContainingIgnoreCase(name);
        return accountCategoryMapper.mapToListOfAccountCategoriesDto(accountCategories);
    }

    @Override
    public List<AccountCategoryDTO> getAccountCategoriesByAccountId(String accountId) {
        List<AccountCategoryEntity> accountCategories = accountCategoryRepository.findByAccountId(accountId);
        return accountCategoryMapper.mapToListOfAccountCategoriesDto(accountCategories);
    }
}
