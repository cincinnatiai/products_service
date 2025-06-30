package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.entity.Sku;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SkuMapper {

    public SkuDTO mapToSkuDTO(Sku sku){
        return new SkuDTO(
                sku.getId(),
                sku.getName(),
                sku.getUpdated_at(),
                sku.getCreated_at()
        );
    }

    public Sku mapToSku(SkuDTO skuDTO){
        return new Sku(
                skuDTO.getId(),
                skuDTO.getName(),
                skuDTO.getCreated_at(),
                skuDTO.getUpdated_at()
        );
    }

    public List<SkuDTO> mapToSkuDtoList(List<Sku> listOfSku){
        return listOfSku.stream().map(this::mapToSkuDTO).collect(Collectors.toList());
    }
}
