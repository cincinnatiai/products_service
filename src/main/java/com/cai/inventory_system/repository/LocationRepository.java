package com.cai.inventory_system.repository;

import com.cai.inventory_system.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
    boolean existsByTitle(String name);
}
