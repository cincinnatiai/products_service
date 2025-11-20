package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.entity.InventoryItem;
import com.cai.inventory_system.entity.Location;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.InventoryItemMapper;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.service.InventoryItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;
    private InventoryItemMapper inventoryItemMapper;

    @Override
    @NonNull
    public InventoryItemDTO createInventoryItem(@NonNull  InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = inventoryItemMapper.mapToInventoryItem(inventoryItemDTO);
        InventoryItem savedItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.mapToInventoryItemDTO(savedItem);
    }

    @Override
    @NonNull
    public InventoryItemDTO getInventoryItemById(@NonNull String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );
        return inventoryItemMapper.mapToInventoryItemDTO(inventoryItem);
    }

    @Override
    @NonNull
    public List<InventoryItemDTO> getAllInventoryItems() {
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItemRepository.findAll());
    }

    @Override
    public void deleteInventoryItem(@NonNull String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );
        inventoryItemRepository.delete(inventoryItem);
    }

    @Override
    @Nullable
    public InventoryItemDTO updateInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO, @NonNull String id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Item id not found")
        );

        Product product = new Product();
        product.setId(inventoryItemDTO.getProduct_id());

        Location location = new Location();
        location.setId(inventoryItemDTO.getLocation_id());

        inventoryItem.setStatus(inventoryItemDTO.getStatus());
        inventoryItem.setSerial_number(inventoryItemDTO.getSerial_number());
        inventoryItem.setImage(inventoryItemDTO.getImage());
        inventoryItem.setLatitude(inventoryItemDTO.getLatitude());
        inventoryItem.setLongitude(inventoryItemDTO.getLongitude());
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id(inventoryItem.getUser_id());

        log.info("Updating inventory item...");
        log.info(inventoryItem.getStatus());
        InventoryItem updatedItem = inventoryItemRepository.save(inventoryItem);
        log.info("Item with id={} updated", id );
        log.info(updatedItem.getStatus());
        return inventoryItemMapper.mapToInventoryItemDTO(updatedItem);
    }

    @Override
    @NonNull
    public Page<InventoryItemDTO> getInventoryItemsByPage(@NonNull Pageable pageable) {
        Page<InventoryItem> inventoryItems = inventoryItemRepository.findAll(pageable);
        return inventoryItems.map(inventoryItemMapper::mapToInventoryItemDTO);
    }

    @Override
    @NonNull
    public List<InventoryItemDTO> searchInventoryItemsByStatus(@NonNull String status) {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByStatusContainingIgnoreCase(status);
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItems);
    }

    @Override
    @NonNull
    public List<InventoryItemDTO> searchInventoryItemsByProductId(@NonNull String productId) {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByProductId(productId);
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItems);
    }
}