package com.spring.shopapp.service;

import com.spring.shopapp.model.Order;
import com.spring.shopapp.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO save(Order order);

    OrderDTO getById(Long id);

    List<OrderDTO> getAll();

    OrderDTO update(Order order);

    OrderDTO delete(Long id);

    List<OrderDTO> getAllByCustomerId(Long customerId);

    OrderDTO createForCustomer(String customerName, Order order);




}
