package com.example.order.controller;

import com.example.order.entity.Product;
import com.example.order.response.RestPagination;
import com.example.order.response.RestResponse;
import com.example.order.service.ProductService;
import com.example.order.specification.FilterField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> getListProduct(
        @RequestParam(name = "page",defaultValue = "1") int page,
        @RequestParam(name = "pageSize", defaultValue = "12") int pageSize,
        @RequestParam(name = "minPrice",defaultValue = "-1") int minPrice,
        @RequestParam(name = "maxPrice",defaultValue = "-1") int maxPrice,
        @RequestParam(name = "name",required = false) String name,
        @RequestParam(name = "category_id",defaultValue = "-1") int category_id,
        @RequestParam(name = "id",defaultValue = "-1") int id,
        @RequestParam(name = "sortType",defaultValue = "-1") int sortType
    ){
        FilterField productFilter = FilterField.FilterFieldBuilder.aFilterField()
                .withId(id)
                .withCategoryId(category_id)
                .withName(name)
                .withPage(page)
                .withPageSize(pageSize)
                .withMaxPrice(maxPrice)
                .withMinPrice(minPrice)
                .withSortType(sortType)
                .build();
        Page<Product> productPage = productService.getListProduct(productFilter);
        if(productPage.getContent().size() == 0){
            return new ResponseEntity<>(
                    new RestResponse.Success()
                            .setMessage("List is empty")
                            .build()
                    ,HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new RestResponse.Success().addDatas(productPage.getContent()).setPagination(
                        new RestPagination(productPage.getNumber() + 1, productPage.getSize(),productPage.getTotalElements())
                ).build()
                ,HttpStatus.OK);
    }

    //get product by id
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id){
        return new ResponseEntity<>(
                new RestResponse.Success()
                        .addData(productService.getProduct(id)).build()
                ,HttpStatus.OK);
    }

    //create new product
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(productService.save(product))
                        .build()
                ,HttpStatus.OK);
    }
}
