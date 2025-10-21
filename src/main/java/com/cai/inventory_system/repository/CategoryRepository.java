package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
}
