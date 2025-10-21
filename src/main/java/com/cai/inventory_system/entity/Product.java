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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private String qr_code;
    private String created_at;
    private String updated_at;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "sku_id", nullable = true)
    private Sku sku;

    private String account_id;
}
