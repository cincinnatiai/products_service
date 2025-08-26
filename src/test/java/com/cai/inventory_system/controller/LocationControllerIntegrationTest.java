package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.LocationDTO;
import com.cai.inventory_system.entity.Location;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.repository.LocationRepository;
import com.cai.inventory_system.utils.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    private String getRootUrl(){ return "http://localhost:" + port + "/api/locations";}

    @BeforeEach
    public void setup(){
        inventoryItemRepository.deleteAll();
        locationRepository.deleteAll();

    }

    @Test
    public void testCreateLocation(){

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setTitle("Test Location");
        locationDTO.setDescription("Test Description");
        locationDTO.setAddress_line("Test Address Line");
        locationDTO.setCity("Test City");
        locationDTO.setState("Test State");
        locationDTO.setPostal_code("12345");
        locationDTO.setCountry("Test Country");
        locationDTO.setCountry_code("123");
        locationDTO.setLatitude(123f);
        locationDTO.setLongitude(123f);


        ResponseEntity<LocationDTO> response = restTemplate.postForEntity(getRootUrl(), locationDTO, LocationDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Location", response.getBody().getTitle());


    }

    @Test
    public void testGetAllLocations(){
        Location location = new Location();
        location.setTitle("Test Location");
        location.setDescription("Test Description");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        Location location2 = new Location();
        location2.setTitle("Test Location2");
        location2.setDescription("Test Description2");
        location2.setAddress_line("Test Address Line");
        location2.setCity("Test City");
        location2.setState("Test State");
        location2.setPostal_code("12345");
        location2.setCountry("Test Country");
        location2.setCountry_code("123");
        location2.setLatitude(123f);
        location2.setLongitude(123f);

        locationRepository.saveAll(Arrays.asList(location, location2));

        ResponseEntity<LocationDTO[]> response = restTemplate.getForEntity(getRootUrl(), LocationDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);

    }

    @Test
    public void testGetLocationByPage(){
        Location location = new Location();
        location.setTitle("Test Location");
        location.setDescription("Test Description");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        Location location2 = new Location();
        location2.setTitle("Test Location2");
        location2.setDescription("Test Description2");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        locationRepository.saveAll(Arrays.asList(location, location2));
        ResponseEntity<PaginationResponse<LocationDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=title&direction=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<LocationDTO>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetLocationById(){
        Location location = new Location();
        location.setTitle("Test Location");
        location.setDescription("Test Description");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        location = locationRepository.save(location);

        ResponseEntity<LocationDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + location.getId(), LocationDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Location", response.getBody().getTitle());

    }

    @Test
    public void testUpdateLocation(){
        Location location = new Location();
        location.setTitle("Test Location");
        location.setDescription("Test Description");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        location =  locationRepository.save(location);

        LocationDTO updated = new LocationDTO();
        updated.setTitle("Updated Title");
        updated.setDescription("Updated Description");
        updated.setAddress_line("Test Address Line");
        updated.setCity("Test City");
        updated.setState("Test State");
        updated.setPostal_code("12345");
        updated.setCountry("Test Country");
        updated.setCountry_code("123");
        updated.setLatitude(123f);
        updated.setLongitude(123f);

        HttpEntity<LocationDTO> requestEntity = new HttpEntity<>(updated);
        ResponseEntity<LocationDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + location.getId(),
                HttpMethod.PUT,
                requestEntity,
                LocationDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    public void testDeleteLocation(){
        Location location = new Location();
        location.setTitle("Test Location");
        location.setDescription("Test Description");
        location.setAddress_line("Test Address Line");
        location.setCity("Test City");
        location.setState("Test State");
        location.setPostal_code("12345");
        location.setCountry("Test Country");
        location.setCountry_code("123");
        location.setLatitude(123f);
        location.setLongitude(123f);

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getRootUrl() + "/" + location.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        ResponseEntity<LocationDTO> getResponse = restTemplate.getForEntity(getRootUrl() + "/" + location.getId(), LocationDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
