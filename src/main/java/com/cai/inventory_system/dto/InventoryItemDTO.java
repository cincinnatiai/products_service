package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  InventoryItemDTO {

    /** This is our primary key **/
    private String id;
    private String status;
    private String serial_number;
    private String image;
    private Float latitude;
    private Float longitude;
    private String created_at;
    private String updated_at;

    /** This is a foreign key with for a table products **/
    private String product_id;
    private String product_name;

    /** This is a foreign key with for a table locations **/
    private String location_id;
    private String location_title;

    /** This is a foreign key with for a table users **/
    private String user_id;
}
