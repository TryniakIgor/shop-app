package com.spring.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String name;
    private List<Long> ordersDTO;
}
