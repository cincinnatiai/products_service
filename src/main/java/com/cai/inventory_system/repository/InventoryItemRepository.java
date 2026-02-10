package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.InventoryItem;
import com.cai.inventory_system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    List<InventoryItem> findByTitleContainingIgnoreCaseAndAccountId(String title, String accountId);
    List<InventoryItem> findByProductIdAndAccountId(String productId, String accountId);
    List<InventoryItem> findAllByAccountId(String accountId);
    Page<InventoryItem> findByAccountId(Pageable pageable, String accountId);
    Optional<InventoryItem> findByIdAndAccountId(String id, String accountId);
}
