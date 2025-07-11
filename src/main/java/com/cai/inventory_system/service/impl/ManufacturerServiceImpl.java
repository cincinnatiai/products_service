package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.ManufacturerMapper;
import com.cai.inventory_system.repository.ManufacturerRepository;
import com.cai.inventory_system.service.ManufacturerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private ManufacturerMapper manufacturerMapper;
    private ManufacturerRepository manufacturerRepository;

    @Override
    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = manufacturerMapper.mapToManufacturer(manufacturerDTO);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.mapToManufacturerDto(savedManufacturer);
    }
    @Override
    public List<ManufacturerDTO> getAllManufactures() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers.stream().map((manufacturer) -> manufacturerMapper.mapToManufacturerDto(manufacturer))
                .collect(Collectors.toList());
    }

    @Override
    public ManufacturerDTO getManufacturerById(String id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Manufacturer id not found")
        );
        return manufacturerMapper.mapToManufacturerDto(manufacturer);
    }

    @Override
    public ManufacturerDTO updateManufacturer(String id, ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Manufacturer id not found")
        );
        manufacturer.setName(manufacturerDTO.getName());
        manufacturer.setAddress(manufacturerDTO.getAddress());
        manufacturer.setContact(manufacturerDTO.getContact());
        manufacturer.setCreated_at(manufacturerDTO.getCreated_at());
        manufacturer.setUpdated_at(manufacturerDTO.getCreated_at());

        Manufacturer updatedTodo = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.mapToManufacturerDto(updatedTodo);
    }

    @Override
    public Page<ManufacturerDTO> getManufacturersByPage(Pageable pageable) {
        Page<Manufacturer> manufacturers = manufacturerRepository.findAll(pageable);
        return manufacturers.map(manufacturerMapper::mapToManufacturerDto);

    }

    @Override
    public void deleteManufacturer(String id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Manufacturer id not found")
        );
        manufacturerRepository.delete(manufacturer);
    }




}
