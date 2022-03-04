package com.example.inventory.controller;

import com.example.inventory.entity.Category;
import com.example.inventory.mappers.CategoryMapper;
import com.example.inventory.response.RestPagination;
import com.example.inventory.response.RestResponse;
import com.example.inventory.service.category.CategoryService;
import com.example.inventory.specification.ParamField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity<?> getCategories(
        @RequestParam(name = "page",defaultValue = "1") int page,
        @RequestParam(name = "pageSize",defaultValue = "2") int pageSize,
        @RequestParam(name = "name",required = false) String name,
        @RequestParam(name = "id",defaultValue = "-1") long id,
        @RequestParam(name = "sort",defaultValue = "-1") int sort
    ){
        ParamField categoryFilter = ParamField.ParamFieldBuilder.aParamField()
                .withPage(page)
                .withPageSize(pageSize)
                .withName(name)
                .withId(id)
                .withSort(sort)
                .build();
        Page<Category> categoryPage = categoryService.getCategories(categoryFilter);
        return new ResponseEntity<>(
                new RestResponse.Success().addDatas(categoryPage.getContent().stream().map(CategoryMapper.INSTANCE::categoryToCategoryDto).collect(Collectors.toList()))
                        .setPagination(new RestPagination(categoryPage.getNumber() + 1,categoryPage.getSize(),categoryPage.getTotalElements())).build()
                , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(categoryService.getCategory(id)).build(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(categoryService.save(category)).build(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/edit/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @Valid @RequestBody Category category){
        return  new ResponseEntity<>(
                new RestResponse.Success().addData(categoryService.edit(id,category)).build(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return new ResponseEntity<>(
                new RestResponse.Success().setMessage(categoryService.delete(id)).build(),HttpStatus.OK
        );
    }
}
