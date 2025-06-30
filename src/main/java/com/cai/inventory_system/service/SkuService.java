package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.entity.Sku;

import java.util.List;

public interface SkuService {

    SkuDTO createSku(SkuDTO skuDTO);
    SkuDTO getSkuById(String id);
    SkuDTO updateSku(SkuDTO skuDTO, String id);
    SkuDTO deleteSku(String id);
    List<SkuDTO> getAllSku();
}
