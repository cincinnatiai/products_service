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
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id){
        ProductDTO productDTOFoundById = productService.getProductById(id);
        return new ResponseEntity<>(productDTOFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        ProductDTO productDTOCreated = productService.createProduct(productDTO);
        return new ResponseEntity<>(productDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable String id){
        ProductDTO productDTOEdited = productService.updateProduct(productDTO, id);
        return ResponseEntity.ok(productDTOEdited);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir =direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<ProductDTO> products = productService.getProductsByPage(pageable);
        return  ResponseEntity.ok(products);
    }
    @PostMapping("/batch")
    public ResponseEntity<List<ProductDTO>> createBaseProducts(@RequestBody List<ProductDTO> productDTOS){
        List<ProductDTO> createdProducts = productDTOS.stream()
                .map(productService::createProduct)
                .toList();
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

}
