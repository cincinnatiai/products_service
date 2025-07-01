package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.service.SkuService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sku")
public class SkuController {

    private final SkuService skuService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<SkuDTO> createSku(@RequestBody SkuDTO skuDTO){
        SkuDTO skuDTOCreated = skuService.createSku(skuDTO);
        return new ResponseEntity<>(skuDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SkuDTO> getSkuById(@RequestParam String id){
        SkuDTO skuFoundById = skuService.getSkuById(id);
        return new ResponseEntity<>(skuFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SkuDTO>> getAllSku(){
        List<SkuDTO> allSku = skuService.getAllSku();
        return new ResponseEntity<>(allSku, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSkuById(@RequestParam String id){
        skuService.deleteSku(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SkuDTO> updateSkuById(@RequestBody SkuDTO skuDTO ,@RequestParam String id){
        SkuDTO skuDtoUpdated = skuService.updateSku(skuDTO, id);
        return new ResponseEntity<>(skuDtoUpdated, HttpStatus.OK);
    }
}
