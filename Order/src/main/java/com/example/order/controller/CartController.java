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

    //add product to cart
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> addToCard(@RequestParam String access_token,@RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.addToCart(access_token,cartItem))
                .build(), HttpStatus.OK);
    }
    //get list cart item
    @RequestMapping(method = RequestMethod.GET,value = "/getListItem")
    public ResponseEntity<?> getListItems(@RequestParam String access_token){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.findCart(access_token))
                .build(),HttpStatus.OK);
    }
    //update product in cart
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public ResponseEntity<?> updateCard(@RequestParam String access_token, @RequestBody CartItem cartItem){
        return new ResponseEntity<>(new RestResponse.Success()
                .addData(cartService.updateCart(access_token,cartItem)).build()
                ,HttpStatus.OK);
    }

    //remove cart item
    @RequestMapping(method = RequestMethod.GET,value = "/removeCartItem/{id}")
    public ResponseEntity<?> removeCartIem(@RequestParam String access_token,@PathVariable Integer id){
        cartService.removeItem(access_token,id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    //remove cart
    @RequestMapping(method = RequestMethod.DELETE,value = "/delete")
    public ResponseEntity<?> removeCart(@RequestParam String access_token){
        cartService.clear(access_token);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
