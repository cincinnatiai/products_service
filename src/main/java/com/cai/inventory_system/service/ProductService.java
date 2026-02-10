package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService{

    void deleteProductByIdAndAccount(String id, String accountId);
    List<ProductDTO> getProductsByAccountId(String accountId);
    List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId);
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO productDTO, String accountId);
    List<ProductDTO> getAllProductsByAccountId(String accountId);
    ProductDTO getProductByIdAndAccountId(String id, String accountId);
    ProductDTO updateProductByIdAndAccountId(ProductDTO productDTO, String id, String accountId);
    Page<ProductDTO> getProductsByPageAndAccountId(Pageable pageable, String accountId);
    List<ProductDTO> getProductsByIdAndAccountId(String id, String accountId);
    List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId, String accountId);
    List<ProductDTO> searchProductsByNameAndAccountId(String name, String accountId);
    List<ProductDTO> searchProductsByCategoryIdAndAccount(String categoryId, String accountId);

}
