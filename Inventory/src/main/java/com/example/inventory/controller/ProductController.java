package com.example.inventory.controller;


import com.example.inventory.entity.Product;
import com.example.inventory.mappers.ProductMapper;
import com.example.inventory.response.RestPagination;
import com.example.inventory.response.RestResponse;
import com.example.inventory.service.product.ProductService;
import com.example.inventory.specification.ParamField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String Hello(){
        return  "Hello";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity<?> getProducts(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "pageSize",defaultValue = "12") int pageSize,
            @RequestParam(name = "minPrice",defaultValue = "-1") BigDecimal minPrice,
            @RequestParam(name = "maxPrice",defaultValue = "-1") BigDecimal maxPrice,
            @RequestParam(name = "category_id",defaultValue = "-1") long category_id,
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "id",defaultValue = "-1") long id,
            @RequestParam(name = "sort",defaultValue = "-1") int sort
            ){
        ParamField productFilter = ParamField.ParamFieldBuilder.aParamField()
                .withPage(page)
                .withPageSize(pageSize)
                .withId(id)
                .withName(name)
                .withSort(sort)
                .withCategory(category_id)
                .withMinPrice(minPrice)
                .withMaxPrice(maxPrice)
                .build();
        Page<Product> productPage = productService.getProducts(productFilter);
        return new ResponseEntity<>(
                new RestResponse.Success().addDatas(productPage.getContent().stream().map(ProductMapper.INSTANCE::productToProductDto).collect(Collectors.toList())).setPagination(
                        new RestPagination(productPage.getNumber() + 1,productPage.getSize(),productPage.getTotalElements())
                ).build()
        , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(productService.getProduct(id)).build()
        ,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(productService.save(product)).build()
        ,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id,@Valid @RequestBody Product product){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(productService.edit(id,product)).build(),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(
                new RestResponse.Success().setMessage("Success").build()
                ,HttpStatus.OK);
    }
}
