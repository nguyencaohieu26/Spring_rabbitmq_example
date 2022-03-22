package com.example.order.service;

import com.example.order.entity.Cart;
import com.example.order.entity.CartItem;
import com.example.order.exceptions.NotFoundException;
import com.example.order.exceptions.SystemException;
import com.example.order.repository.CartItemRepository;
import com.example.order.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public Cart addToCart(String userID, CartItem cartItem) {
        if(cartItem.getQuantity() <= 0){
            throw new SystemException("Quantity must be greater than 0");
        }
        Cart cartExist = cartRepository.findCartByAccountID(userID);
        if(cartExist != null){
            Map<Long,CartItem> listCartItems = cartExist.getItems();
            if(listCartItems.containsKey(cartItem.getProductID())){
                CartItem cartItemExist =listCartItems.get(cartItem.getProductID());
                cartItemExist.setQuantity(cartItemExist.getQuantity() + 1);
                listCartItems.put(cartItem.getProductID(),cartItemExist);
            }else{
                listCartItems.put(cartItem.getProductID(),cartItem);
                cartItem.setCart(cartExist);
            }
            cartExist.setItems(listCartItems);
            cartExist.setTotalMoney();
            return cartRepository.save(cartExist);
        }
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        newCart.setAccountID(userID);
        HashMap<Long,CartItem> newCartItems = new HashMap<>();
        newCartItems.put(cartItem.getProductID(),cartItem);
        newCart.setItems(newCartItems);
        cartItem.setCart(newCart);
        newCart.setTotalMoney();
        return cartRepository.save(newCart);
    }

    @Override
    public Cart updateCart(String userID, CartItem cartItem) {
        Cart cartExist = cartRepository.findCartByAccountID(userID);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        Map<Long,CartItem> listCartItems = cartExist.getItems();
        if(cartItem.getQuantity() < 0){
            throw new SystemException("Quantity must be greater than 0");
        }
        if(!listCartItems.containsKey(cartItem.getProductID())){
            throw new NotFoundException("Product ID is not found!");
        }
        CartItem cartItemFind = listCartItems.get(cartItem.getProductID());
        listCartItems.remove(cartItem.getProductID());
        cartItemFind.setQuantity(cartItem.getQuantity());
        listCartItems.put(cartItemFind.getProductID(),cartItemFind);
        cartExist.setTotalMoney();
        return cartRepository.save(cartExist);
    }

    @Override
    public Cart findCart(String userID) {
        Cart cartExist = cartRepository.findCartByAccountID(userID);
        if(cartExist != null){
            for (Map.Entry<Long, CartItem> set : cartExist.getItems().entrySet()) {
                // Printing all elements of a Map
                System.out.println(set.getKey());
            }
            return cartExist;
        }
        return null;
    }

    @Transactional
    @Override
    public void removeItem(String userID, Long productID) {
        Cart cartExist = cartRepository.findCartByAccountID(userID);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        Map<Long,CartItem> listCartItems = cartExist.getItems();
        if(listCartItems.get(productID) == null){
            throw new NotFoundException("Cart Item is not found");
        }
        listCartItems.remove(productID);
        cartExist.setItems(listCartItems);
        cartItemRepository.deleteCartItemByProductID(productID);
        cartExist.setTotalMoney();
        cartRepository.save(cartExist);
    }

    @Override
    public void clear(String userID) {
        Cart cartExist = cartRepository.findCartByAccountID(userID);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        cartRepository.deleteById(cartExist.getId());
    }
}
