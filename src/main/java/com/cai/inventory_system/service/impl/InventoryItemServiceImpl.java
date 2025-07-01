package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.entity.InventoryItem;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.InventoryItemMapper;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.service.InventoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;
    private InventoryItemMapper inventoryItemMapper;

    @Override
    public InventoryItemDTO createInventoryItem(InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = inventoryItemMapper.mapToInventoryItem(inventoryItemDTO);
        InventoryItem savedItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.mapToInventoryItemDTO(savedItem);
    }

    @Override
    public InventoryItemDTO getInventoryItemById(String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );
        return inventoryItemMapper.mapToInventoryItemDTO(inventoryItem);
    }

    @Override
    public List<InventoryItemDTO> getAllInventoryItems() {
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItemRepository.findAll());
    }

    @Override
    public void deleteInventoryItem(String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );
        inventoryItemRepository.deleteById(id);
    }

    @Override
    public InventoryItemDTO updateInventoryItem(InventoryItemDTO inventoryItemDTO, String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );
        inventoryItem.setStatus(inventoryItem.getStatus());
        inventoryItem.setSerial_number(inventoryItem.getSerial_number());
        inventoryItem.setImage(inventoryItem.getImage());
        inventoryItem.setLatitude(inventoryItem.getLatitude());
        inventoryItem.setLongitude(inventoryItem.getLongitude());

        InventoryItem updatedItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.mapToInventoryItemDTO(updatedItem);
    }
}
