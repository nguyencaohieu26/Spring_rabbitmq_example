package com.example.order.service;

import com.example.order.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getListProduct();
    Product getProduct(Integer id);
    Product save(Product product);
}
