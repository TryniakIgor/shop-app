package com.spring.shopapp.controller;

import com.spring.shopapp.model.Order;
import com.spring.shopapp.dto.OrderDTO;
import com.spring.shopapp.service.OrderService;
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
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDTO>> getOrders() {

        return ResponseEntity.ok().body(orderService.getAll());
    }

    @PostMapping("/order/add/{customerName}")
    public ResponseEntity<OrderDTO> createForCustomer(@PathVariable String customerName, @RequestBody Order order ) {
        return ResponseEntity.ok().body(orderService.createForCustomer(customerName, order));
    }

    @PutMapping("/order")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.update(order));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.getById(id));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<OrderDTO> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.delete(id));
    }
    @GetMapping("getAllByCustomerId/{customerId}")
    public ResponseEntity<List<OrderDTO>> getAllByUserId(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllByCustomerId(customerId));
    }

}
