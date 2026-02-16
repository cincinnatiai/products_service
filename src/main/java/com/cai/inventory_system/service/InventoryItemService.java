package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.InventoryItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


import java.util.List;

public interface InventoryItemService {

    @NonNull
    InventoryItemDTO createInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO, @NonNull String accountId );

    @Nullable
    InventoryItemDTO getInventoryItemById(@NonNull String id, @NonNull String accountId);

    @NonNull
    List<InventoryItemDTO> getAllInventoryItemsByAccountId(@NonNull String accountId);

    void deleteInventoryItem(@NonNull String id, @NonNull String accountId);

    @Nullable
    InventoryItemDTO updateInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO, @NonNull String id, @NonNull String accountId);

    @NonNull
    Page<InventoryItemDTO> getInventoryItemsByPage(@NonNull Pageable pageable, @NonNull String accountId);

    @NonNull
    List<InventoryItemDTO> searchInventoryItemsByTitle(@NonNull String name, @NonNull String accountId);

    @NonNull
    List<InventoryItemDTO> searchInventoryItemsByProductId(@NonNull String productId, @NonNull String accountId);

    @NonNull
    List<InventoryItemDTO> getAllInventoryItems();
}
