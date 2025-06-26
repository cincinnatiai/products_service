package com.cai.inventory_system.mapper;

import com.cai.inventory_system.dto.ProductDTO;
import com.cai.inventory_system.entity.Product;
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
                product.getManufacturer(),
                product.getCategory(),
                product.getSku()
        );
    }

    public Product mapToProduct(ProductDTO productDTO){
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getQr_code(),
                productDTO.getCreated_at(),
                productDTO.getUpdated_at(),
                productDTO.getManufacturer_id(),
                productDTO.getCategory_id(),
                productDTO.getSku_id()
        );
    }

    public List<ProductDTO> mapToListOfProductDto(List<Product> listOfProducts){
        return listOfProducts.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    public List<Product> mapToListOfProduct(List<ProductDTO> listOfProductsDto){
        return listOfProductsDto.stream().map(this::mapToProduct).collect(Collectors.toList());
    }
}
