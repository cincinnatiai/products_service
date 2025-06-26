package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.entity.InventoryItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryItemMapper {

    public InventoryItemDTO mapToInventoryItemDTO(InventoryItem inventoryItem){
        return new InventoryItemDTO(
                inventoryItem.getId(),
                inventoryItem.getStatus(),
                inventoryItem.getSerial_number(),
                inventoryItem.getImage(),
                inventoryItem.getLatitude(),
                inventoryItem.getLongitude(),
                inventoryItem.getCreated_at(),
                inventoryItem.getUpdated_at(),
                inventoryItem.getProduct(),
                inventoryItem.getClient(),
                inventoryItem.getUser()
        );
    }

    public InventoryItem mapToInventoryItem(InventoryItemDTO inventoryItemDTO){
        return new InventoryItem(
                inventoryItemDTO.getId(),
                inventoryItemDTO.getStatus(),
                inventoryItemDTO.getSerial_number(),
                inventoryItemDTO.getImage(),
                inventoryItemDTO.getLatitude(),
                inventoryItemDTO.getLongitude(),
                inventoryItemDTO.getCreated_at(),
                inventoryItemDTO.getUpdated_at(),
                inventoryItemDTO.getProduct_id(),
                inventoryItemDTO.getClient_id(),
                inventoryItemDTO.getUser_id()
        );
    }

    public List<InventoryItemDTO> mapToListOfInventoryItemsDTO(List<InventoryItem> listOfInventoryItems){
        return listOfInventoryItems.stream().map(this::mapToInventoryItemDTO).collect(Collectors.toList());
    }

    public List<InventoryItem> mapToListOfInventoryItems(List<InventoryItemDTO> listOfInventoryItemsDto){
        return listOfInventoryItemsDto.stream().map(this::mapToInventoryItem).collect(Collectors.toList());
    }
}
