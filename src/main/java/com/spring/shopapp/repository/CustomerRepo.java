package com.spring.shopapp.repository;

import com.spring.shopapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
}
