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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;
    private InventoryItemMapper inventoryItemMapper;
    private final MessageSource messageSource;

    private InventoryItem getInventoryItemOrThrowException(String id, String accountId) {
        return inventoryItemRepository.findByIdAndAccountId(id, accountId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("inventory_item_not_found", null, Locale.getDefault())
                ));
    }

    @Override
    @NonNull
    public InventoryItemDTO createInventoryItem(@NonNull  InventoryItemDTO inventoryItemDTO, @NonNull String accountId) {
        InventoryItem inventoryItem = inventoryItemMapper.mapToInventoryItem(inventoryItemDTO);
        inventoryItem.setAccountId(accountId);
        InventoryItem savedItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.mapToInventoryItemDTO(savedItem);
    }

    @Override
    @NonNull
    public InventoryItemDTO getInventoryItemById(@NonNull String id, @NonNull String accountId) {
        return inventoryItemMapper.mapToInventoryItemDTO(getInventoryItemOrThrowException(id, accountId));
    }

    @NonNull
    @Override
    public List<InventoryItemDTO> getAllInventoryItemsByAccountId(@NonNull String accountId) {
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItemRepository.findAllByAccountId(accountId));
    }

    @Override
    public void deleteInventoryItem(@NonNull String id, @NonNull String accountId) {
        InventoryItem inventoryItem = getInventoryItemOrThrowException(id, accountId);
        inventoryItemRepository.delete(inventoryItem);
    }

    @Override
    @Nullable
    public InventoryItemDTO updateInventoryItem(@NonNull InventoryItemDTO inventoryItemDTO, @NonNull String id, @NonNull String accountId) {
        InventoryItem inventoryItem = getInventoryItemOrThrowException(id, accountId);

        Product product = new Product();
        product.setId(inventoryItemDTO.getProductId());

        Location location = null;
        if (inventoryItemDTO.getLocationId() != null) {
            location = new Location();
            location.setId(inventoryItemDTO.getLocationId());
        }
        inventoryItem.setStatus(inventoryItemDTO.getStatus());
        inventoryItem.setTitle(inventoryItemDTO.getTitle());
        inventoryItem.setDescription(inventoryItem.getDescription());
        inventoryItem.setSerialNumber(inventoryItemDTO.getSerialNumber());
        inventoryItem.setImage(inventoryItemDTO.getImage());
        inventoryItem.setLatitude(inventoryItemDTO.getLatitude());
        inventoryItem.setLongitude(inventoryItemDTO.getLongitude());
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUserId(inventoryItem.getUserId());
        InventoryItem updatedItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItemMapper.mapToInventoryItemDTO(updatedItem);
    }

    @Override
    @NonNull
    public Page<InventoryItemDTO> getInventoryItemsByPage(@NonNull Pageable pageable, @NonNull String accountId) {
        Page<InventoryItem> inventoryItems = inventoryItemRepository.findByAccountId(pageable, accountId);
        return inventoryItems.map(inventoryItemMapper::mapToInventoryItemDTO);
    }

    @Override
    @NonNull
    public List<InventoryItemDTO> searchInventoryItemsByTitle(@NonNull String title, @NonNull String accountId) {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByTitleContainingIgnoreCaseAndAccountId(title, accountId);
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItems);
    }

    @Override
    @NonNull
    public List<InventoryItemDTO> searchInventoryItemsByProductId(@NonNull String productId, @NonNull String accountId) {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByProductIdAndAccountId(productId, accountId);
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItems);
    }

    @NonNull
    @Override
    public List<InventoryItemDTO> getAllInventoryItems(){
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return inventoryItemMapper.mapToListOfInventoryItemsDTO(inventoryItems);
    }
}