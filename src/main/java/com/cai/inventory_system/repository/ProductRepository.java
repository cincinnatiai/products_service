package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String>{

}
