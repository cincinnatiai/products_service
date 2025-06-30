package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
