package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>{

    List<Product> findByAccountId(String accountId);
}
