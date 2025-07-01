package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.InventoryItemDTO;

import java.util.List;

public interface InventoryItemService {
    InventoryItemDTO createInventoryItem(InventoryItemDTO inventoryItemDTO);
    InventoryItemDTO getInventoryItemById(String id);
    List<InventoryItemDTO> getAllInventoryItems();
    void  deleteInventoryItem(String id);
    InventoryItemDTO updateInventoryItem(InventoryItemDTO inventoryItemDTO, String id);
}
