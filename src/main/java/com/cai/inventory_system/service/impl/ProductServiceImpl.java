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
import jakarta.transaction.Transactional;
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

    private Product getProductOrThrowException(String id, String accountId) {
        return productRepository.findByIdAndAccountId(id, accountId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("product_not_found", null, Locale.getDefault())
                ));
    }


    @Override
    public ProductDTO createProduct(ProductDTO productDTO, String accountId) {
        productRepository.findByNameAndAccountId(productDTO.getName(), accountId).ifPresent(
                product -> {
                    throw new ResourceAlreadyExistsException("Product with name " + productDTO.getName() + " already exists");
                }
        );
        Product productToSave = productMapper.mapToProduct(productDTO);
        productToSave.setAccountId(accountId);
        productRepository.save(productToSave);
        return productMapper.mapToProductDto(productToSave);
    }

    @Override
    public ProductDTO getProductByIdAndAccountId(String id, String accountId) {
        return productMapper.mapToProductDto(getProductOrThrowException(id, accountId));
    }

    @Override
    public List<ProductDTO> getAllProductsByAccountId(String accountId) {
        return productMapper.mapToListOfProductDto(productRepository.findByAccountId(accountId));
    }

    @Override
    @Transactional
    public void deleteProductByIdAndAccount(String id, String accountId) {
        Product product = getProductOrThrowException(id, accountId);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByAccountId(String accountId) {
        return List.of();
    }

    @Override
    public List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId) {
        return List.of();
    }

    @Override
    @Transactional
    public ProductDTO updateProductByIdAndAccountId(ProductDTO productDTO, String id, String accountId) {
        Product productToEdit = getProductOrThrowException(id, accountId);
        if (!productToEdit.getName().equals(productDTO.getName())) {
            productRepository.findByNameAndAccountIdAndIdNot(productDTO.getName(), accountId, id).ifPresent(
                    product -> {
                        throw new ResourceAlreadyExistsException(
                                "Product with name " + productDTO.getName() + " already exists for this account"
                        );
                    }
            );
        }
        Manufacturer manufacturer = null;
        if (productDTO.getManufacturer_id() != null) {
            manufacturer = new Manufacturer();
            manufacturer.setId(productDTO.getManufacturer_id());
        }

        Category category = null;
        if (productDTO.getCategory_id() != null) {
            category = new Category();
            category.setId(productDTO.getCategory_id());
        }

        Sku sku = null;
        if (productDTO.getSku_id() != null) {
            sku = new Sku();
            sku.setId(productDTO.getSku_id());
        }

        AccountCategoryEntity accountCategory = null;
        if (productDTO.getAccount_category_id() != null) {
            accountCategory = new AccountCategoryEntity();
            accountCategory.setId(productDTO.getAccount_category_id());
        }

        productToEdit.setName(productDTO.getName());
        productToEdit.setDescription(productDTO.getDescription());
        productToEdit.setQr_code(productDTO.getQr_code());
        productToEdit.setManufacturer(manufacturer);
        productToEdit.setCategory(category);
        productToEdit.setSku(sku);
        productToEdit.setAccountCategory(accountCategory);

        Product updatedProduct = productRepository.save(productToEdit);
        return productMapper.mapToProductDto(updatedProduct);
    }


    @Override
    public Page<ProductDTO> getProductsByPageAndAccountId(Pageable pageable, String accountId) {
        Page<Product> products = productRepository.findByAccountId(pageable, accountId);
        return products.map(productMapper::mapToProductDto);
    }

   @Override
   public List<ProductDTO> getProductsByIdAndAccountId(String id, String accountId) {
        final List<Product> products = productRepository.findByAccountId(accountId);
        return productMapper.mapToListOfProductDto(products);
    }


    @Override
    public List<ProductDTO> getProductsByAccountCategoryId(String accountCategoryId, String accountId) {
        AccountCategoryEntity accountCategory = new AccountCategoryEntity();
        accountCategory.setId(accountCategoryId);
        final List<Product> products = productRepository.findByAccountCategoryAndAccountId(accountCategory, accountId);
        return productMapper.mapToListOfProductDto(products);
    }

    @Override
    public List<ProductDTO> searchProductsByNameAndAccountId(String name, String accountId){
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndAccountId(name, accountId);
        return productMapper.mapToListOfProductDto(products);
    }
    @Override
    public List<ProductDTO> searchProductsByCategoryIdAndAccount(String categoryId, String accountId){
        List<Product> products = productRepository.findByCategoryIdAndAccountId(categoryId,  accountId);
        return productMapper.mapToListOfProductDto(products);
    }
    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.mapToListOfProductDto(productRepository.findAll());
    }

}
