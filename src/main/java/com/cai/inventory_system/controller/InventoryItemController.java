package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.service.InventoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/items")
public class InventoryItemController {

    private InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO){
        InventoryItemDTO inventoryItem =inventoryItemService.createInventoryItem(inventoryItemDTO);
        return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemById(@PathVariable("id") String inventoryItemId){
        InventoryItemDTO inventoryItemDTO = inventoryItemService.getInventoryItemById(inventoryItemId);
        return ResponseEntity.ok(inventoryItemDTO);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItemDTO>> getAllInventoryItems(){
        List<InventoryItemDTO> inventoryItems = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@PathVariable("id") String inventoryItemId,
                                                                @RequestBody InventoryItemDTO updatedItem){
        InventoryItemDTO inventoryItemDTO = inventoryItemService.updateInventoryItem(updatedItem, inventoryItemId);
        return ResponseEntity.ok(inventoryItemDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInventoryItem(@PathVariable("id") String inventoryItemId){
        inventoryItemService.deleteInventoryItem(inventoryItemId);
        return ResponseEntity.ok("The Item was deleted from the Inventory successfully");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<InventoryItemDTO>> getInventoryItemsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<InventoryItemDTO> inventoryItems = inventoryItemService.getInventoryItemsByPage(pageable);
        return ResponseEntity.ok(inventoryItems);
    }
}
