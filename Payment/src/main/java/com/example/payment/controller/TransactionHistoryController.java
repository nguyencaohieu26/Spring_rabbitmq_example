package com.example.payment.controller;

import com.example.payment.response.RestResponse;
import com.example.payment.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactionHistories")
@CrossOrigin("*")
public class TransactionHistoryController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity<?> getAllTransactions(){
        return new ResponseEntity<>(
                new RestResponse.Success().addDatas(transactionService.getAll()).build(),HttpStatus.OK);
    }
}
