package com.example.inventory.service.category;

import com.example.inventory.dto.CategoryDto;
import com.example.inventory.entity.Category;
import com.example.inventory.specification.ParamField;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getCategories(ParamField filter);
    CategoryDto save(Category category);
    CategoryDto getCategory(Long categoryID);
    CategoryDto edit(Long id,Category category);
    String delete(Long categoryID);
}
