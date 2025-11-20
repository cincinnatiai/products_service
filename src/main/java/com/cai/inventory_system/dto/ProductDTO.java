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

    /** This is our primary key **/
    private String id;

    private String name;
    private String description;
    private String qr_code;
    private String created_at;
    private String updated_at;

    /** This is a foreign key with for a table manufacturers **/
    private String manufacturer_id;

    /** This is a foreign key with for a table categories **/
    private String category_id;

    private String category_name;

    /** This is a foreign key with for a table sku **/
    private String sku_id;

    private String account_category_id;

    private String account_id;
}