package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationDTO {
    private String id;
    private String title;
    private String description;
    private String address_line;
    private String city;
    private String state;
    private String postal_code;
    private String country;
    private String country_code;
    private Float latitude;
    private Float longitude;
    private String created_at;
    private String updated_at;

    private String account_id;
    private String user_id;
}
