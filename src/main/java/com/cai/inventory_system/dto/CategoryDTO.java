package com.cai.inventory_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    /** This is our primary key **/
    private String id;

    private String name;

    @JsonProperty("account_id")
    private String account_id;

    @JsonProperty("created_at")
    private LocalDateTime create_at;

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

}