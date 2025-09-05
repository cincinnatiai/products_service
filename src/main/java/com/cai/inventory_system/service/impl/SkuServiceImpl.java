package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.SkuMapper;
import com.cai.inventory_system.repository.SkuRepository;
import com.cai.inventory_system.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private final SkuMapper skuMapper;
    private final SkuRepository skuRepository;
    private final MessageSource messageSource;

    private Sku getProductOrThrowException(String id) {
        return skuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("product_not_found", null, Locale.getDefault())));
    }

    @Override
    public SkuDTO createSku(SkuDTO skuDTO) {
        Sku skuToSave = skuMapper.mapToSku(skuDTO);
        skuRepository.save(skuToSave);
        return skuMapper.mapToSkuDTO(skuToSave);
    }

    @Override
    public SkuDTO getSkuById(String id) {
        return skuMapper.mapToSkuDTO(getProductOrThrowException(id));
    }

    @Override
    public SkuDTO updateSku(SkuDTO skuDTO, String id) {
        Sku skuToEdit = getProductOrThrowException(id);
        skuToEdit.setName(skuDTO.getName());
        skuToEdit.setCreated_at(skuDTO.getCreated_at());
        skuToEdit.setCreated_at(skuDTO.getUpdated_at());
        Sku updatedSku = skuRepository.save(skuToEdit);
        return skuMapper.mapToSkuDTO(updatedSku);
    }

    @Override
    public SkuDTO deleteSku(String id) {
        SkuDTO skuDtoToEraseById = skuMapper.mapToSkuDTO(getProductOrThrowException(id));
        skuRepository.deleteById(id);
        return skuDtoToEraseById;
    }

    @Override
    public List<SkuDTO> getAllSku() {
        return skuMapper.mapToSkuDtoList(skuRepository.findAll());
    }

    @Override
    public Page<SkuDTO> getSkuByPage(Pageable pageable) {
    Page<Sku> skus = skuRepository.findAll(pageable);
        return skus.map(skuMapper::mapToSkuDTO);
    }
}
