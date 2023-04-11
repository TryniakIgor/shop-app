package com.spring.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long customerId;
    private List<ProductDTO> productsDTO;
}
