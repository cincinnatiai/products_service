package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.InventoryItemDTO;
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
}
