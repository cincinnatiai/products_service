package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam String id){
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
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @RequestParam String id){
        ProductDTO productDTOEdited = productService.updateProduct(productDTO, id);
        return new ResponseEntity<>(productDTOEdited, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@RequestParam String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }
}
