package com.cai.inventory_system.dto;

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
    private String manufacturerId;
    private String categoryId;
    private String skuId;
}