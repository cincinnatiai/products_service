package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ManufacturerDTO;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.repository.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManufacturerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/manufacturers";
    }

    @BeforeEach
    public void setup() {
        manufacturerRepository.deleteAll();
    }

    @Test
    public void testCreateManufacturer() {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setName("Manufacturer");
        manufacturerDTO.setAddress("0111 Address");
        manufacturerDTO.setContact("example@mail.com");


        ResponseEntity<ManufacturerDTO> response = restTemplate.postForEntity(getRootUrl(), manufacturerDTO, ManufacturerDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Manufacturer", response.getBody().getName());
    }

    @Test
    public void testGetAllManufacturers() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer.setAddress("0111 Address");
        manufacturer.setContact("example@mail.com");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Manufacturer 2");
        manufacturer2.setAddress("0222 Address");
        manufacturer2.setContact("example2@mail.com");

        manufacturerRepository.saveAll(Arrays.asList(manufacturer, manufacturer2));

        ResponseEntity<ManufacturerDTO[]> response = restTemplate.getForEntity(getRootUrl(), ManufacturerDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testGetManufacturerById() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer.setAddress("0111 Address");
        manufacturer.setContact("example@mail.com");

        manufacturer = manufacturerRepository.save(manufacturer);

        ResponseEntity<ManufacturerDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + manufacturer.getId(), ManufacturerDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Manufacturer", response.getBody().getName());
    }

    @Test
    public void testUpdateManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer.setAddress("0111 Address");
        manufacturer.setContact("example@mail.com");

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerDTO updated = new ManufacturerDTO();
        updated.setName("Updated");
        updated.setAddress("New Address");
        updated.setContact("updated_contact@mail.com");

        HttpEntity<ManufacturerDTO> requestEntity = new HttpEntity<>(updated);

        ResponseEntity<ManufacturerDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + manufacturer.getId(),
                HttpMethod.PUT,
                requestEntity,
                ManufacturerDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getName());
        assertEquals("New Address", response.getBody().getAddress());
        assertEquals("updated_contact@mail.com", response.getBody().getContact());

    }

    @Test
    public void testDeleteManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");


        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getRootUrl() + "/" + manufacturer.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        ResponseEntity<ManufacturerDTO> getResponse = restTemplate.getForEntity(getRootUrl() + "/" + manufacturer.getId(), ManufacturerDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
