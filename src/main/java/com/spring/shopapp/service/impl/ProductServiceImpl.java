package com.spring.shopapp.service.impl;

import com.spring.shopapp.model.Order;
import com.spring.shopapp.model.Product;
import com.spring.shopapp.dto.ProductDTO;
import com.spring.shopapp.exeption.EntityAlreadyExist;
import com.spring.shopapp.exeption.ResourceCannotBeDeletedException;
import com.spring.shopapp.exeption.ResourceNotFoundException;
import com.spring.shopapp.mapper.ProductMapper;
import com.spring.shopapp.repository.OrderRepo;
import com.spring.shopapp.repository.ProductRepo;
import com.spring.shopapp.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public ProductServiceImpl(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public ProductDTO save(Product product) {

        Optional<Product> existingProduct = Optional.ofNullable(productRepo.findByName(product.getName()));
        existingProduct.ifPresent(
                (value) -> {
                    throw new EntityAlreadyExist("Product", product.getName());
                }
        );
        product.setOrders(Collections.emptyList());
        productRepo.save(product);

        return ProductMapper.toDTO(product);
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id: " + id));
        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepo.findAll();
        return Optional.ofNullable(products)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(Product product) {
        Product updatedProduct = productRepo.save(product);
        return ProductMapper.toDTO(updatedProduct);
    }

    @Override
    public ProductDTO delete(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id: " + id));
        if (!product.getOrders().isEmpty()) {
            throw new ResourceCannotBeDeletedException("Product", id, "orders exist");
        }
        productRepo.delete(product);
        return ProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO addProductByIdToOrderById(Long productId, Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id: " + productId));
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id: " + orderId));
        order.getProducts().add(product);
        productRepo.save(product);
        return ProductMapper.toDTO(product);
    }
}

