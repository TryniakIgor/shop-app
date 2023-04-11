package com.spring.shopapp.mapper;

import com.spring.shopapp.model.Customer;
import com.spring.shopapp.model.Order;
import com.spring.shopapp.dto.CustomerDTO;

import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer){
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ordersDTO( customer.getOrders().stream()
                        .map(Order::getId)
                        .collect(Collectors.toList()))
                .build();
    }
//    public static CustomerDTO toDTO(Customer customer, List<OrderDTO> orderDTOs){
//        return CustomerDTO.builder()
//                .id(customer.getId())
//                .name(customer.getName())
//                .ordersDTO(orderDTOs)
//                .build();
//    }
}
