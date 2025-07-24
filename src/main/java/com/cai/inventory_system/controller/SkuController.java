package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.service.SkuService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/skus")
public class SkuController {

    private final SkuService skuService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<SkuDTO> createSku(@RequestBody SkuDTO skuDTO){
        SkuDTO skuDTOCreated = skuService.createSku(skuDTO);
        return new ResponseEntity<>(skuDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SkuDTO> getSkuById(@PathVariable String id){
        SkuDTO skuFoundById = skuService.getSkuById(id);
        return new ResponseEntity<>(skuFoundById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SkuDTO>> getAllSku(){
        List<SkuDTO> allSku = skuService.getAllSku();
        return new ResponseEntity<>(allSku, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSkuById(@PathVariable String id){
        skuService.deleteSku(id);
        return new ResponseEntity<>(messageSource.getMessage("resource_deleted", null, Locale.getDefault()), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SkuDTO> updateSkuById(@RequestBody SkuDTO skuDTO ,@PathVariable String id){
        SkuDTO skuDtoUpdated = skuService.updateSku(skuDTO, id);
        return new ResponseEntity<>(skuDtoUpdated, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SkuDTO>> getManufacturersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<SkuDTO> skus = skuService.getSkuByPage(pageable);
        return ResponseEntity.ok(skus);
    }

}
