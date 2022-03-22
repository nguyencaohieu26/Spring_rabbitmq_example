package com.example.payment.controller;

import com.example.payment.response.RestResponse;
import com.example.payment.service.wallet.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/payment")
public class WalletController {

    @Autowired
    WalletService walletService;

    @RequestMapping(method = RequestMethod.GET,value = "/wallet")
    public ResponseEntity<?> getWallet(@RequestParam(name = "accountID") String id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(walletService.getWallet(id)).build()
                , HttpStatus.OK);
    }
}
