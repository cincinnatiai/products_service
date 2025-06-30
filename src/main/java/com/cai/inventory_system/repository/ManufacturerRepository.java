package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {

}
