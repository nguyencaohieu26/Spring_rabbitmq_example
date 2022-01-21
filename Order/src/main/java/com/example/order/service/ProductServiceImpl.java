package com.example.order.service;

import com.example.order.entity.Product;
import com.example.order.enums.SearchOperation;
import com.example.order.repository.ProductRepository;
import com.example.order.specification.FilterField;
import com.example.order.specification.ProductSpecification;
import com.example.order.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> getListProduct(FilterField field) {
        Specification<Product> spe = Specification.where(null);
        PageRequest paging = null;
        paging = PageRequest.of(field.getPage() - 1,field.getPageSize());
        if(field.getSortType() > -1){
            if(field.getSortType() == 0){
                paging = PageRequest.of(field.getPage() - 1,field.getPageSize(), Sort.Direction.DESC,"name");
            }else if(field.getSortType() == 1){
                paging = PageRequest.of(field.getPage() - 1,field.getPageSize(), Sort.Direction.ASC,"name");
            }else if(field.getSortType() == 2){
                paging = PageRequest.of(field.getPage() - 1,field.getPageSize(), Sort.Direction.ASC,"price");
            }else if(field.getSortType() == 3){
                paging = PageRequest.of(field.getPage() - 1,field.getPageSize(), Sort.Direction.DESC,"price");
            }
        }
        //
        if(field.getName() != null && field.getName().length() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(FilterField.NAME, SearchOperation.EQUALITY,field.getName())));
        }
        if(field.getCategoryId() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(FilterField.CATEGORY_ID,SearchOperation.EQUALITY,field.getCategoryId())));
        }
        //filter by max price
        if(field.getMaxPrice() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(FilterField.PRICE,SearchOperation.LESS_THAN,field.getMaxPrice())));
            System.out.println(field.getMaxPrice());
        }
        //filter by min price
        if(field.getMinPrice() > 0){
            System.out.println(field.getMinPrice());
            spe = spe.and(new ProductSpecification(new SearchCriteria(FilterField.PRICE,SearchOperation.GREATER_THAN,field.getMinPrice())));
        }
        //filter by id
        if(field.getId() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(FilterField.ID,SearchOperation.EQUALITY,field.getId())));
        }
        return productRepository.findAll(spe,paging);
    }

    @Override
    public Product getProduct(Integer id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
