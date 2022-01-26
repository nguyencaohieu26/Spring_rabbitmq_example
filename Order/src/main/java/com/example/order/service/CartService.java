package com.example.order.service;

import com.example.order.entity.Cart;
import com.example.order.entity.CartItem;

public interface CartService {
    Cart addToCart(String access_token,CartItem cartItem);
    Cart updateCart(String access_token,CartItem cartItem);
    Cart findCart(String access_token);
    void removeItem(String access_token,Integer productID);
    void clear(String access_token);
}
