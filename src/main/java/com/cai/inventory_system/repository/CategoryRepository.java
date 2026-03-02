package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByNameContainingIgnoreCase(String name);
    Optional<Category> findByName(String name);

    Optional<Category> findByNameAndAccountId(String name, String accountId);
    List<Category> findAllByAccountId(String accountId);
    Optional<Category> findByIdAndAccountId(String id, String accountId);
    List<Category> findByNameContainingIgnoreCaseAndAccountId(String name, String accountId);
    Page<Category> findByAccountId(Pageable pageable, String accountId);
    Category findCategoryByIdAndAccountId(String id, String accountId);
}
