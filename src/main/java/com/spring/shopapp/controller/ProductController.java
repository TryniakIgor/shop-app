package com.spring.shopapp.controller;

import com.spring.shopapp.model.Product;
import com.spring.shopapp.dto.ProductDTO;
import com.spring.shopapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${url}")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok().body(productService.getAll());
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(productService.save(product));
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(productService.update(product));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getById(id));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.delete(id));
    }

    @PutMapping("addToOrder/{productId}/{orderId}")
    public ResponseEntity<ProductDTO> addProductToOrder(
            @PathVariable Long productId, @PathVariable Long orderId) {
        return ResponseEntity.ok(productService.addProductByIdToOrderById(productId, orderId));
    }

}
