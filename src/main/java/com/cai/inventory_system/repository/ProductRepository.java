package com.cai.inventory_system.repository;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.AccountCategoryEntity;
import com.cai.inventory_system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>{

    List<Product> findByAccountCategory(AccountCategoryEntity accountCategory);
    List<Product> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Optional<Product> findByName(String name);
    List<Product> findByCategoryId(String categoryId);
    Optional<Product> findByNameAndIdNot(String name, String id);
    List<Product> findByAccountId(String accountId);
    Optional<Product> findByIdAndAccountId(String id, String accountId);
    boolean existsByIdAndAccountId(String id, String accountId);
    Optional<Product> findByNameAndAccountId(String name, String accountId);
    boolean existsByNameAndAccountId(String name, String accountId);
    List<Product> findByNameContainingIgnoreCaseAndAccountId(String name, String accountId);
    Page<Product> findByAccountId(Pageable pageable, String accountId);
    List<Product> findByCategoryIdAndAccountId(String categoryId, String accountId);
    List<Product> findByAccountCategoryAndAccountId(AccountCategoryEntity accountCategory, String accountId);
    Optional<Product> findByNameAndAccountIdAndIdNot(String name, String accountId, String id);
    long countByAccountId(String accountId);


}
