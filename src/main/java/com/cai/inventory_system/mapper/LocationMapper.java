package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.LocationDTO;
import com.cai.inventory_system.entity.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public LocationDTO mapToLocationDto(Location location){
        return new LocationDTO(
                location.getId(),
                location.getTitle(),
                location.getDescription(),
                location.getAddress_line(),
                location.getCity(),
                location.getState(),
                location.getPostal_code(),
                location.getCountry(),
                location.getCountry_code(),
                location.getLatitude(),
                location.getLongitude(),
                location.getCreated_at(),
                location.getUpdated_at(),
                location.getAccount_id(),
                location.getUser_id()
        );
    }

    public Location mapToLocation(LocationDTO locationDTO){
        return new Location(
                locationDTO.getId(),
                locationDTO.getTitle(),
                locationDTO.getDescription(),
                locationDTO.getAddress_line(),
                locationDTO.getCity(),
                locationDTO.getState(),
                locationDTO.getPostal_code(),
                locationDTO.getCountry(),
                locationDTO.getCountry_code(),
                locationDTO.getLatitude(),
                locationDTO.getLongitude(),
                locationDTO.getCreated_at(),
                locationDTO.getUpdated_at(),
                locationDTO.getAccount_id(),
                locationDTO.getUser_id()
        );
    }

    public List<LocationDTO> mapToLocationDtoList(List<Location> listOfLocations){
        return listOfLocations.stream().map(this::mapToLocationDto).collect(Collectors.toList());
    }

    public List<Location> mapToLocationList(List<LocationDTO> listOfLocations){
        return listOfLocations.stream().map(this::mapToLocation).collect(Collectors.toList());
    }
}
