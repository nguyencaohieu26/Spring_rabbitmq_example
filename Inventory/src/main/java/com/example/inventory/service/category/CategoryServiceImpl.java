package com.example.inventory.service.category;

import com.example.inventory.dto.CategoryDto;
import com.example.inventory.entity.Category;
import com.example.inventory.enums.SearchOperation;
import com.example.inventory.exceptions.NotFoundException;
import com.example.inventory.mappers.CategoryMapper;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.specification.CategorySpecification;
import com.example.inventory.specification.ParamField;
import com.example.inventory.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategories(ParamField filter) {
        Specification<Category> spe = Specification.where(null);
        PageRequest paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize());
        if(filter.getName() != null && filter.getName().length() > 0){
            spe = spe.and(new CategorySpecification(new SearchCriteria(ParamField.NAME,SearchOperation.EQUALITY,filter.getName())));
        }
        if(filter.getId() > 0){
            spe = spe.and(new CategorySpecification(new SearchCriteria(ParamField.ID, SearchOperation.EQUALITY,filter.getId())));
        }
        return categoryRepository.findAll(spe,paging);
    }

    @Override
    public CategoryDto save(Category category) {
        return CategoryMapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategory(Long categoryID) {
        Optional<Category> categoryExist = categoryRepository.findById(categoryID);
        if(!categoryExist.isPresent()){
            throw new NotFoundException("Category is not found!");
        }
        return CategoryMapper.INSTANCE.categoryToCategoryDto(categoryExist.get());
    }

    @Override
    public CategoryDto edit(Long id, Category category) {
        Optional<Category> categoryExist = categoryRepository.findById(id);
        if(!categoryExist.isPresent()){
            throw new NotFoundException("Category is not found!");
        }
        categoryExist.get().updateCategory(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(categoryExist.get()));
    }

    @Override
    public String delete(Long categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if(!category.isPresent()){
            throw new NotFoundException("Category is not found!");
        }
        categoryRepository.deleteById(categoryID);
        return "Delete category successfully";
    }
}
