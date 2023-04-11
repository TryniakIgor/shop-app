package com.spring.shopapp.repository;

import com.spring.shopapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
Product findByName(String name);
}
