package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.InventoryItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


import java.util.List;

public interface InventoryItemService {

    @NonNull
    InventoryItemDTO createInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO);

    @Nullable
    InventoryItemDTO getInventoryItemById(@NonNull String id);

    @NonNull
    List<InventoryItemDTO> getAllInventoryItems();

    void deleteInventoryItem(@NonNull String id);

    @Nullable
    InventoryItemDTO updateInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO, @NonNull String id);

    @NonNull
    Page<InventoryItemDTO> getInventoryItemsByPage(@NonNull Pageable pageable);

    @NonNull
    List<InventoryItemDTO> searchInventoryItemsByStatus(@NonNull String name);

    @NonNull
    List<InventoryItemDTO> searchInventoryItemsByProductId(@NonNull String productId);
}
