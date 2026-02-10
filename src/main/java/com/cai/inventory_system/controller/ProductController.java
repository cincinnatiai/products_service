package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.service.ProductService;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api/inventory/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id,
                                                     @RequestParam("accountId") String accountId) {
        ProductDTO productDTOFoundById = productService.getProductByIdAndAccountId(id, accountId);
        return new ResponseEntity<>(productDTOFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam("accountId") String accountId) {
        List<ProductDTO> allProducts = productService.getAllProductsByAccountId(accountId);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO,
                                                    @RequestParam("accountId") String accountId) {
        ProductDTO productDTOCreated = productService.createProduct(productDTO, accountId);
        return new ResponseEntity<>(productDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable String id,
                                                    @RequestParam("accountId") String accountId){
        ProductDTO productDTOEdited = productService.updateProductByIdAndAccountId(productDTO, id, accountId);
        return ResponseEntity.ok(productDTOEdited);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id,
                                                @RequestParam("accountId") String accountId
    ) {
        productService.deleteProductByIdAndAccount(id,  accountId);
        return  ResponseEntity.ok("The Product was deleted successfully");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam("accountId") String accountId) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<ProductDTO> products = productService.getProductsByPageAndAccountId(pageable, accountId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{accountId}/all")
    public ResponseEntity<List<ProductDTO>> getProductsByAccountId(
            @PathVariable String accountId) {
        List<ProductDTO> products = productService.getProductsByAccountId(accountId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/account-category/{accountCategoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByAccountCategoryId(
            @PathVariable String accountCategoryId,
            @RequestParam("accountId") String accountId) {
        List<ProductDTO> products = productService.getProductsByAccountCategoryId(accountCategoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name,
                                                                 @RequestParam("accountId") String accountId){
        List<ProductDTO> matchedProducts = productService.searchProductsByNameAndAccountId(name, accountId);
        return new ResponseEntity<>(matchedProducts, HttpStatus.OK);
    }

    @GetMapping("by-category/{categoryId}/all")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(
            @PathVariable String categoryId,
            @RequestParam("accountId") String accountId) {
        List<ProductDTO> products = productService.searchProductsByCategoryIdAndAccount(categoryId, accountId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> allProducts() {
        List<ProductDTO> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }
}
