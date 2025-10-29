package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.AccountCategoryDTO;
import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.service.AccountCategoryService;
import com.cai.inventory_system.service.CategoryService;
import com.cai.inventory_system.service.ProductService;
import com.cincinnatiai.ssr_java.SSR;
import com.cincinnatiai.ssr_java.model.NodeModel;
import lombok.NonNull;
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

    @GetMapping("/accounts/{accountId}/home")
    public ResponseEntity<String> getAccountPageHome(@PathVariable String accountId) {
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
        return ResponseEntity.ok(SSR.toJson(createAccountPageHome(products)));
    }

    @NonNull
    private NodeModel createAccountPageHome(List<ProductDTO> products) {
        return SSR.column()
                .modifier(SSR.modifier().padding(16).verticalScroll())
                .addChild(
                        SSR.card()
                                .elevation(8)
                                .modifier(
                                        SSR.modifier()
                                                .fillMaxWidth()
                                                .paddingBottom(16)
                                )
                                .addChild(
                                        SSR.column()
                                                .modifier(SSR.modifier().padding(16))
                                                .addChild(
                                                        SSR.row()
                                                                .modifier(SSR.modifier().padding(16)
                                                                        .fillMaxWidth())
                                                                .addChild(
                                                                        SSR.text("Products")
                                                                                .textStyle(
                                                                                        SSR.textStyle()
                                                                                                .fontSize(20)
                                                                                                .bold()
                                                                                ).modifier(SSR.modifier().weight(1))
                                                                )
                                                                .addChild(
                                                                        SSR.button("Categories")
                                                                                .action("show-modal:categories")
                                                                                .modifier(SSR.modifier().padding(16))
                                                                )
                                                )
                                                .addChild(
                                                        buildProductsTable(products)
                                                )
                                )
                )
                .build();
    }

    @NonNull
    private NodeModel buildProductsTable(List<ProductDTO> products) {
        return SSR.table()
                .showBorders(true)
                .headerBackgroundColor("#6200EE")
                .addColumn(
                        SSR.column("Product")
                                .weight(2.0f)
                                .horizontalAlignment("start")
                )
                .addColumn(
                        SSR.column("description")
                                .weight(1.5f)
                                .horizontalAlignment("start")
                )
                .addColumn(
                        SSR.column("Category")
                                .weight(1.0f)
                                .horizontalAlignment("center")
                )
                .build();
    }
}
