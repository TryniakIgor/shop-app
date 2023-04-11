package com.spring.shopapp.service;

import com.spring.shopapp.model.Product;
import com.spring.shopapp.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO save(Product product);

    ProductDTO getById(Long id);

    List<ProductDTO> getAll();

    ProductDTO update(Product product);

    ProductDTO delete(Long id);

    ProductDTO addProductByIdToOrderById(Long productId, Long orderId);
}
