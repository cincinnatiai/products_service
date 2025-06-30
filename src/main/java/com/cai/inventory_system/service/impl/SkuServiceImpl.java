package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.SkuMapper;
import com.cai.inventory_system.repository.SkuRepository;
import com.cai.inventory_system.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private final SkuMapper skuMapper;
    private final SkuRepository skuRepository;

    @Override
    public SkuDTO createSku(SkuDTO skuDTO) {
        Sku skuToSave = skuMapper.mapToSku(skuDTO);
        skuRepository.save(skuToSave);
        return skuMapper.mapToSkuDTO(skuToSave);
    }

    @Override
    public SkuDTO getSkuById(String id) {
        return skuMapper.mapToSkuDTO(skuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sku id not found")));
    }

    @Override
    public SkuDTO updateSku(SkuDTO skuDTO, String id) {
        Sku skuToEdit = skuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sku id not found"));
        skuToEdit.setName(skuDTO.getName());
        skuToEdit.setCreated_at(skuDTO.getCreated_at());
        skuToEdit.setCreated_at(skuDTO.getUpdated_at());
        return skuMapper.mapToSkuDTO(skuToEdit);
    }

    @Override
    public SkuDTO deleteSku(String id) {
        SkuDTO skuDtoToEraseById = skuMapper.mapToSkuDTO(skuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sku id not found")));
        skuRepository.deleteById(id);
        return skuDtoToEraseById;
    }

    @Override
    public List<SkuDTO> getAllSku() {
        return skuMapper.mapToSkuDtoList(skuRepository.findAll());
    }
}
