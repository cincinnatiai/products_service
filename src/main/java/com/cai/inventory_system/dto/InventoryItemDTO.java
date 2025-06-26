package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryItemDTO {

    private String id;
    private String status;
    private String serial_number;
    private String image;
    private Float latitude;
    private Float longitude;
    private String created_at;
    private String updated_at;
    private String productId;
    private String clientId;
    private String userId;
}
