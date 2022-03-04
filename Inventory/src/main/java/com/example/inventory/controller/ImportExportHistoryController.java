package com.example.inventory.controller;

import com.example.inventory.entity.ImportExportHistory;
import com.example.inventory.mappers.ImportExportHistoryMapper;
import com.example.inventory.response.RestPagination;
import com.example.inventory.response.RestResponse;
import com.example.inventory.service.importExportHistory.importExportHistoryService;
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
@RequestMapping("api/v1/importExportHistories")
public class ImportExportHistoryController {

    @Autowired
    importExportHistoryService importExportHistoryService;

    @RequestMapping(method = RequestMethod.GET,value="/list")
    public ResponseEntity<?> getAllImportExportHistories(
            @RequestParam(name ="page",defaultValue = "1") int page,
            @RequestParam(name ="orderID",defaultValue = "-1") long orderID,
            @RequestParam(name ="pageSize",defaultValue = "10") int pageSize,
            @RequestParam(name ="type",defaultValue = "-1") int type,
            @RequestParam(name ="sort",defaultValue = "-1") int sort,
            @RequestParam(name ="id",defaultValue = "-1") long id,
            @RequestParam(name ="productID",defaultValue = "-1") long productID
    ){
        ParamField importExportHistoryFilter = ParamField.ParamFieldBuilder.aParamField()
                .withPage(page)
                .withPageSize(pageSize)
                .withSort(sort)
                .withId(id)
                .withType(type)
                .withOrder(orderID)
                .withProduct(productID)
                .build();
        Page<ImportExportHistory> importExportHistoriesPage = importExportHistoryService.getImportExportHistories(importExportHistoryFilter);
        return new ResponseEntity<>(
                new RestResponse.Success()
                        .addDatas(importExportHistoriesPage.getContent().stream().map(ImportExportHistoryMapper.INSTANCE::importExportHistoryToImportExportHistoryDto).collect(Collectors.toList()))
                        .setPagination(new RestPagination(importExportHistoriesPage.getNumber()+1,importExportHistoriesPage.getSize(),importExportHistoriesPage.getTotalElements())).build()
                ,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get/{id}")
    public ResponseEntity<?> getImportExportHistory(@PathVariable long id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(importExportHistoryService.getImportExportHistory(id)).build()
                ,HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public ResponseEntity<?> createImportExportHistory(@Valid ImportExportHistory importExportHistory){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(importExportHistoryService.save(importExportHistory)).build()
                ,HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/edit/{id}")
    public ResponseEntity<?> updateImportExportHistory(@PathVariable long id,@Valid ImportExportHistory importExportHistory){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(importExportHistoryService.edit(id,importExportHistory)).build()
                ,HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{id}")
    public ResponseEntity<?> deleteImportExportHistory(@PathVariable long id){
        return new ResponseEntity<>(
                new RestResponse.Success().setMessage(importExportHistoryService.delete(id)).build(),
                HttpStatus.OK
        );
    }
}
