package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    List<InventoryItem> findByTitleContainingIgnoreCase(String title);
    List<InventoryItem> findByProductId(String productId);
}
