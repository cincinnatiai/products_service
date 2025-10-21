package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, String> {

}
