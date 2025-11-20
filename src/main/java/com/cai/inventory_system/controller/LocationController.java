package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.LocationDTO;
import com.cai.inventory_system.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/locations")
@CrossOrigin("*")
public class LocationController {

    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO){
        LocationDTO location = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable String id){
        LocationDTO location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations(){
        List<LocationDTO> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO updatedLocation,
                                                      @PathVariable String id){
        LocationDTO locationDTO = locationService.updateLocation(updatedLocation, id);
        return ResponseEntity.ok(locationDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable String id){
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("Location deleted");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<LocationDTO>> getAllLocationsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<LocationDTO> locations = locationService.getLocationsByPage(pageable);
        return ResponseEntity.ok(locations);
    }
}
