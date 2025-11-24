package com.cai.inventory_system.service.impl;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.AccountCategoryEntity;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.entity.Sku;
import com.cai.inventory_system.exception.ResourceAlreadyExistsException;
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
        productRepository.findByName(productDTO.getName()).ifPresent(
                product -> {
                    throw new ResourceAlreadyExistsException("Product with name " + productDTO.getName() + " already exists");
                }
        );
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
       Product product = productRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("Product with id " + id + " not found")
       );
        productRepository.delete(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String id) {

        productRepository.findByNameAndIdNot(productDTO.getName(), id ).ifPresent(
                product -> {
                    throw new ResourceAlreadyExistsException("Product with name " + productDTO.getName() + " already exists");
                }
        );

        Manufacturer manufacturer = null;
        if (productDTO.getManufacturer_id() != null) {
            manufacturer = new Manufacturer();
            manufacturer.setId(productDTO.getManufacturer_id());
        }


        Category category = new Category();
        category.setId(productDTO.getCategory_id());

        Sku sku = null;
        if(productDTO.getSku_id() != null){
            sku = new Sku();
            sku.setId(productDTO.getSku_id());
        }

        AccountCategoryEntity accountCategory = null;
        if (productDTO.getAccount_category_id() != null) {
            accountCategory = new AccountCategoryEntity();
            accountCategory.setId(productDTO.getAccount_category_id());
        }

        Product productToEdit = getProductOrThrowException(id);
        productToEdit.setName(productDTO.getName());
        productToEdit.setDescription(productDTO.getDescription());
        productToEdit.setQr_code(productDTO.getQr_code());
        productToEdit.setCreated_at(productDTO.getCreated_at());
        productToEdit.setUpdated_at(productDTO.getUpdated_at());
        productToEdit.setManufacturer(manufacturer);
        productToEdit.setCategory(category);
        productToEdit.setSku(sku);
        productToEdit.setAccountCategory(accountCategory);
        productToEdit.setAccountId(productDTO.getAccount_id());
        Product updatedProduct = productRepository.save(productToEdit);

        return productMapper.mapToProductDto(updatedProduct);
    }

    @Override
    public Page<ProductDTO> getProductsByPage(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::mapToProductDto);
    }

    @Override
    public List<ProductDTO> getProductsByAccountId(String accountId) {
        final List<Product> products = productRepository.findByAccountId(accountId);
        return productMapper.mapToListOfProductDto(products);
    }

    @Override
    public Page<ProductDTO> getProductsByAccountId(String accountId, Pageable pageable) {
        Page<Product> products = productRepository.findByAccountId(accountId, pageable);
        return products.map(productMapper::mapToProductDto);
    }

    @Override
    public List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId) {
        AccountCategoryEntity accountCategory = new AccountCategoryEntity();
        accountCategory.setId(accountCategoryId);
        final List<Product> products = productRepository.findByAccountCategory(accountCategory);
        return productMapper.mapToListOfProductDto(products);
    }

    @Override
    public List<ProductDTO> searchProductsByName(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.mapToListOfProductDto(products);
    }
    @Override
    public List<ProductDTO> searchProductsByCategoryId(String categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.mapToListOfProductDto(products);
    }

}
