package com.cai.inventory_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_categories", indexes = {
    @Index(name = "idx_account_id", columnList = "account_id")
})
public class AccountCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    // Should be userId - don't need a foreign key here as this is just for auditing purposes
    @Column(name = "created_by")
    private String createdBy;

    // Foreign Key for Accounts
    @Column(name = "account_id")
    private String accountId;
}
