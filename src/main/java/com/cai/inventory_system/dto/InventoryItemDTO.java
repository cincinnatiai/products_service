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
public class  InventoryItemDTO {

    /**
     * This is our primary key
     **/
    private String id;
    private String status;
    private String title;
    private String description;

    @JsonProperty("serial_number")
    private String serialNumber;

    private String image;
    private Float latitude;
    private Float longitude;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("location_id")
    private String locationId;

    @JsonProperty("location_title")
    private String locationTitle;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("account_id")
    private String accountId;
}
