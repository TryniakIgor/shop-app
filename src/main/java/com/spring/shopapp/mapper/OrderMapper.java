package com.spring.shopapp.mapper;

import com.spring.shopapp.model.Order;
import com.spring.shopapp.dto.OrderDTO;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .productsDTO(order.getProducts().stream().map(ProductMapper::toDTO).collect(Collectors.toList()))
                .build();
    }


}
