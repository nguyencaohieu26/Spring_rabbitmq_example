package com.example.order.controller;

import com.example.order.entity.Category;
import com.example.order.response.RestResponse;
import com.example.order.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //get all categories
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(
                new RestResponse.Success().addDatas(categoryService.getAll()).build()
                , HttpStatus.OK);
    }

    //create category
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> CreateCategory(@Valid @RequestBody Category category){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(categoryService.save(category)).build()
                ,HttpStatus.OK);
    }
}
