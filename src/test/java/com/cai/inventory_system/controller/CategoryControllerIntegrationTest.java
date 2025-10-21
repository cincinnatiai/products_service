package com.cai.inventory_system.controller;

import com.cai.inventory_system.dto.CategoryDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.repository.CategoryRepository;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/categories";
    }

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

    }

    @Test
    public void testCreateCategory(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Category");

        ResponseEntity<CategoryDTO> response = restTemplate.postForEntity(getRootUrl(), categoryDTO, CategoryDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Category", response.getBody().getName());

    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setName("Category");

        Category category2 = new Category();
        category2.setName("Category2");

        categoryRepository.saveAll(Arrays.asList(category, category2));

        ResponseEntity<CategoryDTO[]> response = restTemplate.getForEntity(getRootUrl(), CategoryDTO[].class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testGetCategoriesByPage() {
        Category category = new Category();
        category.setName("Category");

        Category category2 = new Category();
        category2.setName("Category2");

        categoryRepository.saveAll(Arrays.asList(category, category2));
        ResponseEntity<PaginationResponse<CategoryDTO>> response = restTemplate.exchange(
                getRootUrl() + "/page?page=0&size=10&sortBy=name&sortDir=asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginationResponse<CategoryDTO>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void testGetCategoryById() {
        Category category = new Category();
        category.setName("Category");

        category = categoryRepository.save(category);

        ResponseEntity<CategoryDTO> response = restTemplate.getForEntity(getRootUrl() + "/" + category.getId(), CategoryDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Category", response.getBody().getName());
    }

    @Test
    public void testUpdateCategoryById() {
        Category category = new Category();
        category.setName("Category");

        category = categoryRepository.save(category);

        CategoryDTO updated = new CategoryDTO();
        updated.setName("Updated Category");

        HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(updated);

        ResponseEntity<CategoryDTO> response = restTemplate.exchange(
                getRootUrl() + "/" + category.getId(),
                HttpMethod.PUT,
                requestEntity,
                CategoryDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Category", response.getBody().getName());
    }


}
