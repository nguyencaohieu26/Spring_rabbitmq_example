package com.example.order.controller;

import com.example.order.entity.CartItem;
import com.example.order.response.RestResponse;
import com.example.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> addToCard(@RequestHeader("Access-Token") String access_token ,@RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.addToCart(access_token,cartItem))
                .build(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/getCart")
    public ResponseEntity<?> getListItems(@RequestHeader("Access-Token") String access_token){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.findCart(access_token))
                .build(),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT,value = "/update")
    public ResponseEntity<?> updateCard(@RequestHeader("Access-Token") String access_token, @RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.updateCart(access_token,cartItem)).build()
                ,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT,value = "/removeCartItem/{id}")
    public ResponseEntity<?> removeCartIem(@RequestHeader("Access-Token") String access_token,@PathVariable Long id){
        cartService.removeItem(access_token,id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE,value = "/delete")
    public ResponseEntity<?> removeCart(@RequestHeader("Access-Token") String access_token){
        cartService.clear(access_token);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
