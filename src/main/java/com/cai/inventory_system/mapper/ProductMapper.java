package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.Category;
import com.cai.inventory_system.entity.Manufacturer;
import com.cai.inventory_system.entity.Product;
import com.cai.inventory_system.entity.Sku;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDTO mapToProductDto(Product product){

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQr_code(),
                product.getCreated_at(),
                product.getUpdated_at(),
                product.getManufacturer().getId(),
                product.getCategory().getId(),
                product.getSku().getId()
        );
    }

    public Product mapToProduct(ProductDTO productDTO){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(productDTO.getManufacturer_id());

        Category category = new Category();
        category.setId(productDTO.getCategory_id());

        Sku sku = new Sku();
        sku.setId(productDTO.getSku_id());

        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getQr_code(),
                productDTO.getCreated_at(),
                productDTO.getUpdated_at(),
                manufacturer,
                category,
                sku
        );
    }

    public List<ProductDTO> mapToListOfProductDto(List<Product> listOfProducts){
        return listOfProducts.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    public List<Product> mapToListOfProduct(List<ProductDTO> listOfProductsDto){
        return listOfProductsDto.stream().map(this::mapToProduct).collect(Collectors.toList());
    }
}
