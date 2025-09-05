package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.repository.*;
import com.cai.inventory_system.utils.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;



    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkuRepository skuRepository;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    @BeforeEach
    public void setup() {
        inventoryItemRepository.deleteAll();
        productRepository.deleteAll();
        manufacturerRepository.deleteAll();
        categoryRepository.deleteAll();
        skuRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product");
        productDTO.setDescription("Description");
        productDTO.setQr_code("1234567890");
        productDTO.setManufacturer_id(manufacturer.getId());
        productDTO.setCategory_id(category.getId());
        productDTO.setSku_id(sku.getId());


        ResponseEntity<ProductDTO> response = restTemplate.postForEntity(getRootUrl(), productDTO, ProductDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product", response.getBody().getName());
    }

    @Test
    public void testGetAllProducts() {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        Product product = new Product();
        product.setName("Product1");
        product.setDescription("Description 1");
        product.setQr_code("1234567890");
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setSku(sku);


        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("Description 2");
        product2.setQr_code("0987654321");
        product2.setManufacturer(manufacturer);
        product2.setCategory(category);
        product2.setSku(sku);

        productRepository.saveAll(Arrays.asList(product, product2));

        ResponseEntity<PaginationResponse<ProductDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=name&direction=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<ProductDTO>>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());

    }

    @Test
    public void testGetProductByPage(){

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        Product product = new Product();
        product.setName("Product1");
        product.setDescription("Description 1");
        product.setQr_code("1234567890");
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setSku(sku);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("Description 2");
        product2.setQr_code("0987654321");
        product2.setManufacturer(manufacturer);
        product2.setCategory(category);
        product2.setSku(sku);

        productRepository.saveAll(Arrays.asList(product, product2));
        ResponseEntity<PaginationResponse<ProductDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=name&direction=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<ProductDTO>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetProductById() {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description 1");
        product.setQr_code("1234567890");
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setSku(sku);

        product = productRepository.save(product);

        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + product.getId(), ProductDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product", response.getBody().getName());
    }

    @Test
    public void testUpdateProduct() {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description 1");
        product.setQr_code("1234567890");
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setSku(sku);

        product = productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName("Updated");
        productDTO.setDescription("Updated Description");
        productDTO.setQr_code("0987654321");
        productDTO.setManufacturer_id(manufacturer.getId());
        productDTO.setCategory_id(category.getId());
        productDTO.setSku_id(sku.getId());

        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(productDTO);

        ResponseEntity<ProductDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + product.getId(),
                HttpMethod.PUT,
                requestEntity,
                ProductDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated", response.getBody().getName());
    }

    @Test
    public void testDeleteProduct() {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer = manufacturerRepository.save(manufacturer);

        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Sku sku = new Sku();
        sku.setName("SKU");
        sku = skuRepository.save(sku);

        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description 1");
        product.setQr_code("1234567890");
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setSku(sku);


        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getRootUrl() + "/" + product.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        ResponseEntity<ProductDTO> getResponse = restTemplate.getForEntity(getRootUrl() + "/" + product.getId(), ProductDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}