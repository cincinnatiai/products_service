package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.InventoryItemDTO;
import com.cai.inventory_system.entity.InventoryItem;
import com.cai.inventory_system.entity.Location;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.repository.InventoryItemRepository;
import com.cai.inventory_system.repository.LocationRepository;
import com.cai.inventory_system.repository.ProductRepository;
import com.cai.inventory_system.utils.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class InventoryItemControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    private String getRootUrl() { return "http://localhost:" + port + "/api/inventory-items"; }

    @BeforeEach
    public void setup(){
        inventoryItemRepository.deleteAll();
        productRepository.deleteAll();
        locationRepository.deleteAll();

    }

    @Test
    public void testCreateInventoryItem(){
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO();
        inventoryItemDTO.setStatus("ACTIVE");
        inventoryItemDTO.setSerial_number("1234");
        inventoryItemDTO.setImage("image");
        inventoryItemDTO.setLatitude(1331.123f);
        inventoryItemDTO.setLongitude(0987654.543f);
        inventoryItemDTO.setLocation_id(location.getId());
        inventoryItemDTO.setProduct_id(product.getId());
        inventoryItemDTO.setUser_id("325");

        ResponseEntity<InventoryItemDTO> response = restTemplate.postForEntity(getRootUrl(), inventoryItemDTO, InventoryItemDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ACTIVE", response.getBody().getStatus());
    }

    @Test
    public void testGetAllInventoryItems() {
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setStatus("ACTIVE");
        inventoryItem.setSerial_number("1234");
        inventoryItem.setImage("image");
        inventoryItem.setLatitude(1331.123f);
        inventoryItem.setLongitude(0987654.543f);
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id("325");

        InventoryItem inventoryItem2 = new InventoryItem();
        inventoryItem2.setStatus("ACTIVE");
        inventoryItem2.setSerial_number("1234");
        inventoryItem2.setImage("image");
        inventoryItem2.setLatitude(1331.123f);
        inventoryItem2.setLongitude(0987654.543f);
        inventoryItem2.setProduct(product);
        inventoryItem2.setLocation(location);
        inventoryItem2.setUser_id("325");


        inventoryItemRepository.saveAll(Arrays.asList(inventoryItem, inventoryItem2));

        ResponseEntity<InventoryItemDTO[]> response = restTemplate.getForEntity(getRootUrl(), InventoryItemDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }
    @Test
    public void testGetManufacturerByPage() {
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setStatus("ACTIVE");
        inventoryItem.setSerial_number("1234");
        inventoryItem.setImage("image");
        inventoryItem.setLatitude(1331.123f);
        inventoryItem.setLongitude(0987654.543f);
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id("325");

        InventoryItem inventoryItem2 = new InventoryItem();
        inventoryItem2.setStatus("ACTIVE");
        inventoryItem2.setSerial_number("1234");
        inventoryItem2.setImage("image");
        inventoryItem2.setLatitude(1331.123f);
        inventoryItem2.setLongitude(0987654.543f);
        inventoryItem2.setProduct(product);
        inventoryItem2.setLocation(location);
        inventoryItem2.setUser_id("325");

        inventoryItemRepository.saveAll(Arrays.asList(inventoryItem, inventoryItem2));
        ResponseEntity<PaginationResponse<InventoryItemDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=status&direction=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<InventoryItemDTO>>() {}
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetInventoryItemById() {
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setStatus("ACTIVE");
        inventoryItem.setSerial_number("1234");
        inventoryItem.setImage("image");
        inventoryItem.setLatitude(1331.123f);
        inventoryItem.setLongitude(0987654.543f);
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id("325");

        inventoryItem = inventoryItemRepository.save(inventoryItem);

        ResponseEntity<InventoryItemDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + inventoryItem.getId(), InventoryItemDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ACTIVE", response.getBody().getStatus());

    }

    @Test
    public void testUpdateInventoryItem() {
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setStatus("ACTIVE");
        inventoryItem.setSerial_number("1234");
        inventoryItem.setImage("image");
        inventoryItem.setLatitude(1331.123f);
        inventoryItem.setLongitude(0987654.543f);
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id("325");

        inventoryItem = inventoryItemRepository.save(inventoryItem);

        InventoryItemDTO updated = new InventoryItemDTO();
        updated.setStatus("UPDATED");
        updated.setSerial_number("5678");
        updated.setImage("new_image");
        updated.setLatitude(1234.567f);
        updated.setLongitude(8765.432f);
        updated.setProduct_id(product.getId());
        updated.setLocation_id(location.getId());
        updated.setUser_id("3251");

        HttpEntity<InventoryItemDTO> requestEntity = new HttpEntity<>(updated);
        ResponseEntity<InventoryItemDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + inventoryItem.getId(),
                HttpMethod.PUT,
                requestEntity,
                InventoryItemDTO.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UPDATED", response.getBody().getStatus());

    }

    @Test
    public void testDeleteInventoryItem() {
        Product product = new Product();
        product.setName("product");
        product = productRepository.save(product);

        Location location = new Location();
        location.setTitle("location");
        location = locationRepository.save(location);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setStatus("ACTIVE");
        inventoryItem.setSerial_number("1234");
        inventoryItem.setImage("image");
        inventoryItem.setLatitude(1331.123f);
        inventoryItem.setLongitude(0987654.543f);
        inventoryItem.setProduct(product);
        inventoryItem.setLocation(location);
        inventoryItem.setUser_id("325");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getRootUrl() + "/" + inventoryItem.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
                );

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        ResponseEntity<InventoryItemDTO> getResponse = restTemplate.getForEntity(getRootUrl() + "/" + inventoryItem.getId(), InventoryItemDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

}
