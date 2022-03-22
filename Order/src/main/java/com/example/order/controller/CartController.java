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


    @RequestMapping(method = RequestMethod.POST,value = "/add/{userID}")
    public ResponseEntity<?> addToCard(@PathVariable String userID ,@RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.addToCart(userID,cartItem))
                .build(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/getCart/{userID}")
    public ResponseEntity<?> getListItems(@PathVariable String userID){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.findCart(userID))
                .build(),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT,value = "/update/{userID}")
    public ResponseEntity<?> updateCard(@PathVariable String userID, @RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.updateCart(userID,cartItem)).build()
                ,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT,value = "/removeCartItem/{id}/{userID}")
    public ResponseEntity<?> removeCartIem(@PathVariable String userID,@PathVariable Long id){
        cartService.removeItem(userID,id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{userID}")
    public ResponseEntity<?> removeCart(@PathVariable  String userID){
        cartService.clear(userID);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
