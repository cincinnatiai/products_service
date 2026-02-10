package com.cai.inventory_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

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