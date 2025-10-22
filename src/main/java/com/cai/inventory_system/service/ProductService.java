package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService{
    ProductDTO createProduct (ProductDTO productDTO);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    void deleteProduct(String id);
    ProductDTO updateProduct(ProductDTO productDTO, String id);
    Page<ProductDTO> getProductsByPage(Pageable pageable);
    List<ProductDTO> getProductsByAccountId(String accountId);
    Page<ProductDTO> getProductsByAccountId(String accountId, Pageable pageable);
    List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId);
}