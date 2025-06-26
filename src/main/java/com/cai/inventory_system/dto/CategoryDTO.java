package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    /** This is our primary key **/
    private String id;

    private String name;
    private String created_at;
    private String updated_at;
}