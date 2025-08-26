package com.cai.inventory_system.service;

import com.cai.inventory_system.dto.LocationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface LocationService {

    @org.springframework.lang.NonNull
    LocationDTO createLocation(@NonNull LocationDTO locationDTO);

    @org.springframework.lang.Nullable
    LocationDTO getLocationById(@NonNull String id);

    @NonNull
    List<LocationDTO> getAllLocations();

    void deleteLocationById(@NonNull String id);

    @Nullable
    LocationDTO updateLocation(@NonNull LocationDTO locationDTO, @NonNull String id);

    @NonNull
    Page<LocationDTO> getLocationsByPage(@NonNull Pageable pageable);

}
