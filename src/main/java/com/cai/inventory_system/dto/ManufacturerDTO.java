package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManufacturerDTO {

    private String id;
    private String name;
    private String address;
    private String contact;
    private String created_at;
    private String updated_at;
}
