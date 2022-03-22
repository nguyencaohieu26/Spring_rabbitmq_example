package com.example.order.service;

import com.example.order.entity.Cart;
import com.example.order.entity.CartItem;

public interface CartService {
    void clear(String userID);
    Cart findCart(String userID);
    Cart addToCart(String userID,CartItem cartItem);
    Cart updateCart(String userID,CartItem cartItem);
    void removeItem(String userID,Long productID);
}
