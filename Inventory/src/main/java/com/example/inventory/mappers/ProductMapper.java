package com.example.inventory.mappers;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto productToProductDto(Product product);
}
