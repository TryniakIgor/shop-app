package com.spring.shopapp.service.impl;

import com.spring.shopapp.model.Customer;
import com.spring.shopapp.model.Order;
import com.spring.shopapp.model.Product;
import com.spring.shopapp.dto.OrderDTO;
import com.spring.shopapp.exeption.ResourceNotFoundException;
import com.spring.shopapp.mapper.OrderMapper;
import com.spring.shopapp.repository.CustomerRepo;
import com.spring.shopapp.repository.OrderRepo;
import com.spring.shopapp.repository.ProductRepo;
import com.spring.shopapp.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;

    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public OrderDTO save(Order order) {
        order.setProducts(Collections.emptyList());
        orderRepo.save(order);
        return OrderMapper.toDTO(order) ;
    }

    @Override
    @Transactional
    public OrderDTO createForCustomer(String customerName, Order order) {
        Optional<Customer> customerOptional = Optional.of(customerRepo.findByName(customerName));
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer", "Name: " + customerName);
        }
        Customer customer = customerOptional.get();
        order.setCustomer(customer);
        order.setProducts(Collections.emptyList());
        orderRepo.save(order);
        return OrderMapper.toDTO(order);
    }

    @Override
    public OrderDTO getById(Long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (orderOptional.isPresent()) {
            return OrderMapper.toDTO(orderOptional.get());
        } else {
            throw new ResourceNotFoundException("Order", "id: " + id);
        }
    }

    @Override
    public List<OrderDTO> getAllByCustomerId(Long customerId) {
        List<Order> orders = orderRepo.findAllByCustomerId(customerId);
        return Optional.ofNullable(orders)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepo.findAll();
        return Optional.ofNullable(orders)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO update(Order order) {
        Optional<Order> orderOptional = orderRepo.findById(order.getId());
        if (orderOptional.isPresent()) {
            order.setCustomer(customerRepo.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id: " + order.getCustomer().getId())));
            List<Product> products = productRepo.findAllById(order.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
            if (products.size() != order.getProducts().size()) {
                throw new ResourceNotFoundException("Product", "one or more ids not found");
            }
            order.setProducts(products);
            Order savedOrder = orderRepo.save(order);
            return OrderMapper.toDTO(savedOrder);
        } else {
            throw new ResourceNotFoundException("Order", "id: " + order.getId());
        }
    }

    @Override
    public OrderDTO delete(Long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderRepo.delete(order);
            return OrderMapper.toDTO(order);
        } else {
            throw new ResourceNotFoundException("Order", "id: " + id);
        }
    }
}
