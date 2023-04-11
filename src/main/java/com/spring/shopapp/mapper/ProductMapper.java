package com.spring.shopapp.mapper;

import com.spring.shopapp.model.Product;
import com.spring.shopapp.dto.ProductDTO;

public class ProductMapper {
    public static ProductDTO toDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

}
