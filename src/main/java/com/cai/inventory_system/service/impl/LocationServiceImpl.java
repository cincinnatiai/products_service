package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.LocationDTO;
import com.cai.inventory_system.entity.Location;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.LocationMapper;
import com.cai.inventory_system.repository.LocationRepository;
import com.cai.inventory_system.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private LocationMapper locationMapper;

    @Override
    @NonNull
    public LocationDTO createLocation(@NonNull LocationDTO locationDTO) {
        Location location = locationMapper.mapToLocation(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.mapToLocationDto(savedLocation);
    }

    @Override
    @NonNull
    public LocationDTO getLocationById(@NonNull String id) {
        Location location = locationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Location not found with id: " + id)
        );
        return locationMapper.mapToLocationDto(location);
    }

    @Override
    @NonNull
    public List<LocationDTO> getAllLocations() {
        return locationMapper.mapToLocationDtoList(locationRepository.findAll());
    }

    @Override
    public void deleteLocationById(@NonNull String id) {
        Location location = locationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Location not found with id: " + id)
        );
        locationRepository.delete(location);
    }

    @Override
    @NonNull
    public LocationDTO updateLocation(@NonNull LocationDTO locationDTO, @NonNull String id) {
        Location location = locationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Location not found with id: " + id)
        );
        location.setTitle(locationDTO.getTitle());
        location.setDescription(locationDTO.getDescription());
        location.setAddress_line(locationDTO.getAddress_line());
        location.setCity(locationDTO.getCity());
        location.setState(locationDTO.getState());
        location.setCountry(locationDTO.getCountry());
        location.setCountry_code(location.getCountry_code());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setCreated_at(locationDTO.getCreated_at());
        location.setUpdated_at(locationDTO.getUpdated_at());
        location.setAccount_id(locationDTO.getAccount_id());
        location.setUser_id(locationDTO.getUser_id());

        Location updatedLocation = locationRepository.save(location);
        return locationMapper.mapToLocationDto(updatedLocation);
    }

    @Override
    @NonNull
    public Page<LocationDTO> getLocationsByPage(@NonNull Pageable pageable) {
        Page<Location> locations = locationRepository.findAll(pageable);
        return locations.map(locationMapper::mapToLocationDto);
    }
}
