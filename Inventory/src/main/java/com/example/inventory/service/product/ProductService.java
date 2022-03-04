package com.example.inventory.service.product;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.entity.Product;
import com.example.inventory.specification.ParamField;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> getProducts(ParamField filter);
    ProductDto save(Product product);
    ProductDto getProduct(Long productID);
    ProductDto edit(Long id,Product product);
    void delete(Long productID);
    void updateQuantity(Long productID,int quantity);
}
