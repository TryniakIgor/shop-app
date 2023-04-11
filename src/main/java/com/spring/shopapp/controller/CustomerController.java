package com.spring.shopapp.controller;

import com.spring.shopapp.model.Customer;
import com.spring.shopapp.dto.CustomerDTO;
import com.spring.shopapp.service.CustomerService;
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
public class CustomerController {
@Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok().body(customerService.save(customer));
    }

    @PutMapping("/customer")
    public ResponseEntity<CustomerDTO> updatecustomer( @RequestBody Customer customer) {
        return ResponseEntity.ok().body(customerService.update(customer));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getById(id));
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.delete(id));
    }
}
