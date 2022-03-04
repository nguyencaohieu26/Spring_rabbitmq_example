package com.example.inventory.controller;

import com.example.inventory.entity.Supplier;
import com.example.inventory.mappers.SupplierMapper;
import com.example.inventory.response.RestPagination;
import com.example.inventory.response.RestResponse;
import com.example.inventory.service.supplier.SupplierService;
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
@RequestMapping("api/v1/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity<?> getSuppliers(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "pageSize",defaultValue = "5") int pageSize,
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(name = "id",defaultValue = "-1") long id,
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "phone",required = false) String phone,
            @RequestParam(name = "sort",defaultValue = "-1") int sort
    ){
        ParamField supplierFilter = ParamField.ParamFieldBuilder.aParamField()
                .withId(id)
                .withPage(page)
                .withPageSize(pageSize)
                .withName(name)
                .withPhone(phone)
                .withEmail(email)
                .withSort(sort)
                .build();
        Page<Supplier> supplierPage = supplierService.getSuppliers(supplierFilter);
        return new ResponseEntity<>(
                new RestResponse.Success()
                        .addDatas(supplierPage.getContent().stream().map(SupplierMapper.INSTANCE::supplierToSupplierDto).collect(Collectors.toList()))
                        .setPagination(new RestPagination(supplierPage.getNumber()+1,supplierPage.getSize(), supplierPage.getTotalElements()))
                        .build()
                , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get/{id}")
    public ResponseEntity<?> getSupplier(@PathVariable Long id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(supplierService.getSupplier(id)).build()
                ,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody Supplier supplier){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(supplierService.save(supplier)).build()
                ,HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/edit/{id}")
    public ResponseEntity<?> editSupplier(@PathVariable Long id,@Valid @RequestBody Supplier supplier){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(supplierService.edit(id,supplier)).build(),HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
        ;
        return new ResponseEntity<>(
               new RestResponse.Success().setMessage(supplierService.delete(id)).build()
                ,HttpStatus.OK
        );
    }
}
