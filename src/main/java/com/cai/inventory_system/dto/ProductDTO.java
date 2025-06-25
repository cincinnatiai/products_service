package com.cai.inventory_system.dto;

import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Sku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private String qr_code;
    private String created_at;
    private String updated_at;
    private Manufacturer manufacturer;
    private Category category;
    private Sku sku;
}