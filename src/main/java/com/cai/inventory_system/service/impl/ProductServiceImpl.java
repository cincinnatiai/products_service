package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.exception.ResourceNotFoundException;
import com.cai.inventory_system.mapper.ProductMapper;
import com.cai.inventory_system.repository.ProductRepository;
import com.cai.inventory_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productToSave = productMapper.mapToProduct(productDTO);
        productRepository.save(productToSave);
        return productMapper.mapToProductDto(productToSave);
    }

    @Override
    public ProductDTO getProductById(String id) {
        return productMapper.mapToProductDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Id not found")));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.mapToListOfProductDto(productRepository.findAll());
    }

    @Override
    public ProductDTO deleteProduct(String id) {
        ProductDTO productDtoToDeleteById = productMapper.mapToProductDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Id not found")));
        productRepository.deleteById(id);
        return productDtoToDeleteById;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String id) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(productDTO.getManufacturer_id());

        Category category = new Category();
        category.setId(productDTO.getCategory_id());

        Sku sku = new Sku();
        sku.setId(productDTO.getSku_id());

        Product productToEdit = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Id not found"));
        productToEdit.setName(productDTO.getName());
        productToEdit.setDescription(productToEdit.getDescription());
        productToEdit.setQr_code(productToEdit.getQr_code());
        productToEdit.setCreated_at(productDTO.getCreated_at());
        productToEdit.setUpdated_at(productToEdit.getUpdated_at());
        productToEdit.setManufacturer(manufacturer);
        productToEdit.setCategory(category);
        productToEdit.setSku(sku);

        return productMapper.mapToProductDto(productToEdit);
    }
}
