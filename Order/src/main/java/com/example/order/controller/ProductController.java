package com.example.order.controller;

import com.example.order.entity.Product;
import com.example.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    //get list product
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity<?> getListProduct(){
        return new ResponseEntity<>(productService.getListProduct(),HttpStatus.OK);
    }

    //get product by id
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id){
        return new ResponseEntity<>(productService.getProduct(id),HttpStatus.OK);
    }

    //create new product
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product),HttpStatus.OK);
    }
}
