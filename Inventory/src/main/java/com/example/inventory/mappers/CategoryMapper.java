package com.example.inventory.mappers;

import com.example.inventory.dto.CategoryDto;
import com.example.inventory.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDto categoryToCategoryDto(Category category);
}
