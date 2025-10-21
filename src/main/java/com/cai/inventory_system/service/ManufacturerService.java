package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.ManufacturerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManufacturerService {
    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);
    List<ManufacturerDTO> getAllManufactures();
    ManufacturerDTO getManufacturerById(String id);
    void deleteManufacturer(String id);
    ManufacturerDTO updateManufacturer(String id, ManufacturerDTO manufacturerDTO);
    Page<ManufacturerDTO> getManufacturersByPage(Pageable pageable);



}
