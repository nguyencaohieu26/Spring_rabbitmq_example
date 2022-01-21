package com.example.order.service;

import com.example.order.entity.Product;
import com.example.order.specification.FilterField;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getListProduct(FilterField filter);
    Product getProduct(Integer id);
    Product save(Product product);
}
