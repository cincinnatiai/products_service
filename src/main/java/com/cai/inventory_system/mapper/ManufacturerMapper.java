package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.entity.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper {

    public ManufacturerDTO mapToManufacturerDto(Manufacturer manufacturer){
        return new ManufacturerDTO(
                manufacturer.getId(),
                manufacturer.getName(),
                manufacturer.getAddress(),
                manufacturer.getContact(),
                manufacturer.getCreated_at(),
                manufacturer.getUpdated_at()
        );
    }

    public Manufacturer mapToManufacturer(ManufacturerDTO manufacturerDTO){
        return new Manufacturer(
                manufacturerDTO.getId(),
                manufacturerDTO.getName(),
                manufacturerDTO.getAddress(),
                manufacturerDTO.getContact(),
                manufacturerDTO.getCreated_at(),
                manufacturerDTO.getUpdated_at()
        );
    }
}
