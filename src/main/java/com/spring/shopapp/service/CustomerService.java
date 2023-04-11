package com.spring.shopapp.service;

import com.spring.shopapp.model.Customer;
import com.spring.shopapp.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO save(Customer customer);

    CustomerDTO getById(Long id);

    List<CustomerDTO> getAll();

    CustomerDTO update(Customer customer);

    CustomerDTO delete(Long id);
}
