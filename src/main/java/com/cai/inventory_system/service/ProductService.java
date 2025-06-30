package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.ProductDTO;

import java.util.List;

public interface ProductService{
    ProductDTO createProduct (ProductDTO productDTO);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    ProductDTO deleteProduct(String id);
    ProductDTO updateProduct(ProductDTO productDTO, String id);
}