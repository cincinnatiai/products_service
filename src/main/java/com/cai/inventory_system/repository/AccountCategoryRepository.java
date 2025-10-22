package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.AccountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountCategoryRepository extends JpaRepository<AccountCategoryEntity, String> {
    List<AccountCategoryEntity> findByAccountId(String accountId);
    List<AccountCategoryEntity> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Optional<AccountCategoryEntity> findByName(String name);
}
