package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.service.ManufacturerService;
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
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    private ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO manufacturer = manufacturerService.createManufacturer(manufacturerDTO);
        return new ResponseEntity<>(manufacturer,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturerById(@PathVariable("id")String manufacturerId ){
        ManufacturerDTO manufacturerDTO = manufacturerService.getManufacturerById(manufacturerId);
        return ResponseEntity.ok(manufacturerDTO);
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers(){
        List<ManufacturerDTO> manufacturers = manufacturerService.getAllManufactures();
        return ResponseEntity.ok(manufacturers);
    }

    @PutMapping("{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable("id") String manufacturerId,
                                                              @RequestBody ManufacturerDTO updatedManufacturer){
        ManufacturerDTO manufacturerDTO = manufacturerService.updateManufacturer(manufacturerId, updatedManufacturer);
        return ResponseEntity.ok(manufacturerDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteManufacturer(@PathVariable("id") String manufacturerId){
        manufacturerService.deleteManufacturer(manufacturerId);
        return ResponseEntity.ok("Manufacturer deleted successfully!");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ManufacturerDTO>> getManufacturersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<ManufacturerDTO> manufacturers = manufacturerService.getManufacturersByPage(pageable);
        return ResponseEntity.ok(manufacturers);
    }

}
