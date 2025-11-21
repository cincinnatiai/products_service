package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.AccountCategoryEntity;
import com.cai.inventory_system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>{

    List<Product> findByAccountId(String accountId);

    Page<Product> findByAccountId(String accountId, Pageable pageable);

    List<Product> findByAccountCategory(AccountCategoryEntity accountCategory);
    List<Product> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Optional<Product> findByName(String name);
    List<Product> findByCategoryId(String categoryId);

}
