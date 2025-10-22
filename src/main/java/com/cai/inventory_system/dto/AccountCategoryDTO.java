package com.cai.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCategoryDTO {

    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    // Should be userId - don't need a foreign key here as this is just for auditing purposes
    private String createdBy;
    // Foreign Key for Accounts
    private String accountId;
}
