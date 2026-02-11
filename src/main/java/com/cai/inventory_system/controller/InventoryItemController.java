package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.service.InventoryItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/inventory/items")
@CrossOrigin("*")
public class InventoryItemController {

    private InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO,
                                                                @RequestHeader("x-account-id") String accountId){
        InventoryItemDTO inventoryItem =inventoryItemService.createInventoryItem(inventoryItemDTO, accountId);
        return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemById(@PathVariable("id") String inventoryItemId,
                                                                 @RequestHeader("x-account-id") String accountId){
        InventoryItemDTO inventoryItemDTO = inventoryItemService.getInventoryItemById(inventoryItemId, accountId);
        return ResponseEntity.ok(inventoryItemDTO);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItemDTO>> getAllInventoryItems(@RequestHeader("x-account-id") String accountId){
        List<InventoryItemDTO> inventoryItems = inventoryItemService.getAllInventoryItemsByAccountId(accountId);
        return ResponseEntity.ok(inventoryItems);
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@PathVariable("id") String inventoryItemId,
                                                                @RequestBody InventoryItemDTO updatedItem,
                                                                @RequestHeader("x-account-id") String accountId){
        InventoryItemDTO inventoryItemDTO = inventoryItemService.updateInventoryItem(updatedItem, inventoryItemId, accountId);
        return ResponseEntity.ok(inventoryItemDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInventoryItem(@PathVariable("id") String inventoryItemId,
                                                      @RequestHeader("x-account-id") String accountId){
        inventoryItemService.deleteInventoryItem(inventoryItemId, accountId);
        return ResponseEntity.ok("The Item was deleted from the Inventory successfully");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<InventoryItemDTO>> getInventoryItemsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestHeader("x-account-id") String accountId)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<InventoryItemDTO> inventoryItems = inventoryItemService.getInventoryItemsByPage(pageable, accountId);
        return ResponseEntity.ok(inventoryItems);
    }

    @GetMapping("/search")
    public ResponseEntity<List<InventoryItemDTO>> searchInventoryItemsByName(
            @RequestParam("title") String title,
            @RequestParam("x-account-id") String accountId) {
        List<InventoryItemDTO> inventoryItems = inventoryItemService.searchInventoryItemsByTitle(title, accountId);
        return ResponseEntity.ok(inventoryItems);
    }

    @GetMapping("/by-product/{productId}/all")
    public ResponseEntity<List<InventoryItemDTO>> getProductsByProductId(@PathVariable String productId,
                                                                         @RequestHeader("x-account-id") String accountId){
        List<InventoryItemDTO> products = inventoryItemService.searchInventoryItemsByProductId(productId, accountId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryItemDTO>> allInventoryItems(){
            List<InventoryItemDTO> inventoryItems = inventoryItemService.getAllInventoryItems();
            return ResponseEntity.ok(inventoryItems);
    }
}
