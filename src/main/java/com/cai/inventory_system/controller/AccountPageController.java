package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import com.cai.inventory_system.dto.AccountPageHomeDTO;
import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.service.AccountCategoryService;
import com.cai.inventory_system.service.CategoryService;
import com.cai.inventory_system.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/pages")
@CrossOrigin("*")
@Slf4j
public class AccountPageController {

    private static final int LOAD_TIMEOUT_SECONDS = 8;
    private static final int PROCESS_TIMEOUT_SECONDS = 8;
    private static final int THREAD_POOL_SIZE = 3;

    private final CategoryService categoryService;
    private final ProductService productService;
    private final AccountCategoryService accountCategoryService;
    private final MessageSource messageSource;
    private final ExecutorService executorService;

    public AccountPageController(
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

    @GetMapping("/accounts/home")
    public ResponseEntity<AccountPageHomeDTO> getAccountPageHome(@PathVariable String accountId) {
        final CountDownLatch latch = new CountDownLatch(2);
        List<AccountCategoryDTO> categories = new ArrayList<>();
        List<ProductDTO> products = new ArrayList<>();
        executorService.submit(() -> {
            try {
                categories.addAll(accountCategoryService.getAccountCategoriesByAccountId(accountId));
            } catch (Exception exception) {
                log.error("AccountPageController.GetAccountPageHomeFailure - Failed to fetch categories for accountId: {}, Message: {}", accountId, exception.getMessage());
            } finally {
                latch.countDown();
            }
        });
        executorService.submit(() -> {
            try {
                products.addAll(productService.getProductsByAccountId(accountId));
            } catch (Exception exception) {
                log.error("AccountPageController.GetAccountPageHomeFailure - Failed to fetch products for accountId: {}, Message: {}", accountId, exception.getMessage());
            } finally {
                latch.countDown();
            }
        });

    }
}
