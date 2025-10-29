package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import com.cai.inventory_system.service.AccountCategoryService;
import com.cai.inventory_system.service.CategoryService;
import com.cai.inventory_system.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {

    private static final int LOAD_TIMEOUT_SECONDS = 8;
    private static final int PROCESS_TIMEOUT_SECONDS = 8;
    private static final int THREAD_POOL_SIZE = 3;

    private final CategoryService categoryService;
    private final ProductService productService;
    private final AccountCategoryService accountCategoryService;
    private final MessageSource messageSource;
    private final ExecutorService executorService;

    public AccountController(
            CategoryService categoryService,
            ProductService productService,
            AccountCategoryService accountCategoryService,
            MessageSource messageSource
    ) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.accountCategoryService = accountCategoryService;
        this.messageSource = messageSource;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @GetMapping("/{accountId}/categories")
    public ResponseEntity<List<AccountCategoryDTO>> getAccountCategories(@PathVariable String accountId) {
        final List<AccountCategoryDTO> categories = accountCategoryService.getAccountCategoriesByAccountId(accountId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{accountId}/categories/{id}")
    public ResponseEntity<AccountCategoryDTO> getAccountCategoryById(
            @PathVariable String accountId,
            @PathVariable String id) {
        final AccountCategoryDTO category = accountCategoryService.getAccountCategoryById(id);
        if (!category.getAccountId().equalsIgnoreCase(accountId)) {
            return new ResponseEntity<>(category, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/{accountId}/categories")
    public ResponseEntity<AccountCategoryDTO> createAccountCategory(
            @PathVariable String accountId,
            @RequestBody AccountCategoryDTO accountCategoryDTO) {
        accountCategoryDTO.setAccountId(accountId);
        AccountCategoryDTO createdCategory = accountCategoryService.createAccountCategory(accountCategoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}/categories/{id}")
    public ResponseEntity<AccountCategoryDTO> updateAccountCategory(
            @PathVariable String accountId,
            @PathVariable String id,
            @RequestBody AccountCategoryDTO accountCategoryDTO) {
        accountCategoryDTO.setAccountId(accountId);
        AccountCategoryDTO updatedCategory = accountCategoryService.updateAccountCategoryById(accountCategoryDTO, id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}/categories/{id}")
    public ResponseEntity<String> deleteAccountCategory(
            @PathVariable String accountId,
            @PathVariable String id) {
        accountCategoryService.deleteAccountCategoryById(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @GetMapping("/{accountId}/categories/page")
    public ResponseEntity<Page<AccountCategoryDTO>> getAccountCategoriesByPage(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        final Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        final Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<AccountCategoryDTO> categories = accountCategoryService.getAccountCategoriesByPage(pageable);
        return ResponseEntity.ok(categories);
    }

}
