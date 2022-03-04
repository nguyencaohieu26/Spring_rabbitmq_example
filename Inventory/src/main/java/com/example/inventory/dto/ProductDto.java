package com.example.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String thumbnail;
    private int inStock;
    private CategoryDto category;
    private SupplierDto supplier;
}
