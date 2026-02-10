package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.entity.InventoryItem;
import com.cai.inventory_system.entity.Location;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryItemMapper {

    public InventoryItemDTO mapToInventoryItemDTO(InventoryItem inventoryItem){
        return new InventoryItemDTO(
                inventoryItem.getId(),
                inventoryItem.getStatus(),
                inventoryItem.getTitle(),
                inventoryItem.getDescription(),
                inventoryItem.getSerialNumber(),
                inventoryItem.getImage(),
                inventoryItem.getLatitude(),
                inventoryItem.getLongitude(),
                inventoryItem.getCreatedAt(),
                inventoryItem.getUpdatedAt(),
                inventoryItem.getProduct().getId(),
                inventoryItem.getProduct().getName(),
                inventoryItem.getLocation() != null ? inventoryItem.getLocation().getId() : null,
                inventoryItem.getLocation() != null ? inventoryItem.getLocation().getTitle() : null,
                inventoryItem.getUserId(),
                inventoryItem.getAccountId()
        );
    }

    public InventoryItem mapToInventoryItem(InventoryItemDTO inventoryItemDTO){
        Product product = new Product();
        product.setId(inventoryItemDTO.getProductId());

        Location location = null;
        if (inventoryItemDTO.getLocationId() != null) {
            location = new Location();
            location.setId(inventoryItemDTO.getLocationId());
        }


        return new InventoryItem(
                inventoryItemDTO.getId(),
                inventoryItemDTO.getStatus(),
                inventoryItemDTO.getTitle(),
                inventoryItemDTO.getDescription(),
                inventoryItemDTO.getSerialNumber(),
                inventoryItemDTO.getImage(),
                inventoryItemDTO.getLatitude(),
                inventoryItemDTO.getLongitude(),
                inventoryItemDTO.getCreated_at(),
                inventoryItemDTO.getUpdated_at(),
                product,
                location,
                inventoryItemDTO.getUserId(),
                inventoryItemDTO.getAccountId()
        );
    }

    public List<InventoryItemDTO> mapToListOfInventoryItemsDTO(List<InventoryItem> listOfInventoryItems){
        return listOfInventoryItems.stream().map(this::mapToInventoryItemDTO).collect(Collectors.toList());
    }

    public List<InventoryItem> mapToListOfInventoryItems(List<InventoryItemDTO> listOfInventoryItemsDto){
        return listOfInventoryItemsDto.stream().map(this::mapToInventoryItem).collect(Collectors.toList());
    }
}
