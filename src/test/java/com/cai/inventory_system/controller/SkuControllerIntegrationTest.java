package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.SkuDTO;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.repository.ProductRepository;
import com.cai.inventory_system.repository.SkuRepository;
import com.cai.inventory_system.utils.PaginationResponse;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkuControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductRepository productRepository;

    private String getRootUrl() { return "http://localhost:" + port + "/api/skus"; }

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        skuRepository.deleteAll();
    }

    @Test
    public void testCreateSku() {
        SkuDTO skuDTO = new SkuDTO();
        skuDTO.setName("SKU");

        ResponseEntity<SkuDTO> response = restTemplate.postForEntity(getRootUrl(), skuDTO, SkuDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SKU", response.getBody().getName());
    }

    @Test
    public void testGetAllSkus() {
        Sku sku1 = new Sku();
        sku1.setName("SKU1");

        Sku sku2 = new Sku();
        sku2.setName("SKU2");

        skuRepository.saveAll(Arrays.asList(sku1, sku2));
        ResponseEntity<SkuDTO[]> response = restTemplate.getForEntity(getRootUrl(), SkuDTO[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testGetManufacturersByPage() {
        Sku sku1 = new Sku();
        sku1.setName("SKU1");
        Sku sku2 = new Sku();
        sku2.setName("SKU2");

        skuRepository.saveAll(Arrays.asList(sku1, sku2));
        ResponseEntity<PaginationResponse<SkuDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=name&direction=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<SkuDTO>>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetSkuById() {
        Sku sku = new Sku();
        sku.setName("SKU");

        sku = skuRepository.save(sku);

        ResponseEntity<SkuDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + sku.getId(), SkuDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.print("Response Body: " + response.getBody());
        assertEquals("SKU", response.getBody().getName());
    }

    @Test
    public void testUpdateSku(){
        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        SkuDTO updatedSku = new SkuDTO();
        updatedSku.setName("Updated SKU");

        HttpEntity<SkuDTO> requestEntity = new HttpEntity<>(updatedSku);

        ResponseEntity<SkuDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + sku.getId(),
                HttpMethod.PUT,
               requestEntity,
                SkuDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated SKU", response.getBody().getName());
    }

    @Test
    public void testDeleteSku(){
        Sku sku = new Sku();
        sku.setName("SKU");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getRootUrl() + "/" + sku.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        ResponseEntity<SkuDTO> getResponse = restTemplate.getForEntity(getRootUrl() + "/" + sku.getId(), SkuDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

}
