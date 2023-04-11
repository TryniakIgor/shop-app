package com.spring.shopapp.service.impl;

import com.spring.shopapp.model.Customer;
import com.spring.shopapp.dto.CustomerDTO;
import com.spring.shopapp.exeption.EntityAlreadyExist;
import com.spring.shopapp.exeption.ResourceCannotBeDeletedException;
import com.spring.shopapp.exeption.ResourceNotFoundException;
import com.spring.shopapp.mapper.CustomerMapper;
import com.spring.shopapp.repository.CustomerRepo;
import com.spring.shopapp.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    public CustomServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerDTO save(Customer customer) {

        Optional<Customer> existingCustomer = Optional.ofNullable(customerRepo.findByName(customer.getName()));
        existingCustomer.ifPresentOrElse(
                (value) -> {
                    throw new EntityAlreadyExist("Customer", customer.getName());
                }, () -> {
                    customer.setOrders(Collections.emptyList());
                    customerRepo.save(customer);
                }
        );
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getById(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id: " + id));
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream().map(CustomerMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO update(Customer customer) {
        Optional<Customer> customerOptional = customerRepo.findById(customer.getId());
        if (customerOptional.isPresent()) {
            Customer savedCustomer = customerRepo.save(customer);
            return CustomerMapper.toDTO(savedCustomer);
        } else {
            throw new ResourceNotFoundException("Customer", "id: " + customer.getId());
        }
    }

    @Override
    public CustomerDTO delete(Long id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (!customer.getOrders().isEmpty()) {
                throw new ResourceCannotBeDeletedException("Customer",  id, "orders exist");
            }
            customerRepo.delete(customer);
            return CustomerMapper.toDTO(customer);
        } else {
            throw new ResourceNotFoundException("Customer", "id: " + id);
        }
    }
}

