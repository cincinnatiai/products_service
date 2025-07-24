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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final MessageSource messageSource;

    private Product getProductOrThrowException(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("product_not_found", null, Locale.getDefault())));
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productToSave = productMapper.mapToProduct(productDTO);
        productRepository.save(productToSave);
        return productMapper.mapToProductDto(productToSave);
    }

    @Override
    public ProductDTO getProductById(String id) {
        return productMapper.mapToProductDto(getProductOrThrowException(id));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.mapToListOfProductDto(productRepository.findAll());
    }

    @Override
    public void deleteProduct(String id) {
        ProductDTO productDtoToDeleteById = productMapper.mapToProductDto(getProductOrThrowException(id));
        productRepository.deleteById(id);

    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String id) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(productDTO.getManufacturer_id());

        Category category = new Category();
        category.setId(productDTO.getCategory_id());

        Sku sku = new Sku();
        sku.setId(productDTO.getSku_id());

        Product productToEdit = getProductOrThrowException(id);
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

    @Override
    public Page<ProductDTO> getProductsByPage(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::mapToProductDto);
    }
}
