package com.example.order.service;

import com.example.order.entity.Cart;
import com.example.order.entity.CartItem;
import com.example.order.exceptions.NotFoundException;
import com.example.order.exceptions.SystemException;
import com.example.order.repository.CartItemRepository;
import com.example.order.repository.CartRepository;
import com.example.order.repository.ProductRepository;
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
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public Cart addToCart(String access_token,CartItem cartItem) {
        //check quantity
        if(cartItem.getQuantity() <= 0){
            throw new SystemException("Quantity must be greater than 0");
    }
        Cart cartExist = cartRepository.findCartAccess_token(access_token);
        if(cartExist != null){
            Map<Integer,CartItem> listCartItems = cartExist.getItems();
            //find product in cart
            if(listCartItems.containsKey(cartItem.getProductID())){
                listCartItems.get(cartItem.getProductID()).setQuantity(cartItem.getQuantity()+1);
            }else{
                listCartItems.put(cartItem.getProductID(),cartItem);
                cartItem.setCart(cartExist);
            }
            //save list after update into card
            cartExist.setItems(listCartItems);
            cartExist.setTotalMoney();
            return cartRepository.save(cartExist);
        }
        //create new cart if not exist
        Cart newCart = new Cart();
        //save cart into database
        Cart cartSave   = cartRepository.save(newCart);
        //save access_token for that cart
        newCart.setAccess_token(access_token);
        //create new list cart item
        HashMap<Integer,CartItem> newCartItems = new HashMap<>();
        //add new cart item to list

        newCartItems.put(cartItem.getProductID(),cartItem);
        //add list cart item to cart
        cartSave.setItems(newCartItems);
        cartItem.setCart(cartSave);
        cartSave.setTotalMoney();
        return cartRepository.save(cartSave);
    }

    @Override
    public Cart updateCart(String access_token, CartItem cartItem) {
        //get cart exist
        Cart cartExist = cartRepository.findCartAccess_token(access_token);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        Map<Integer,CartItem> listCartItems = cartExist.getItems();
        //check quantity
        if(cartItem.getQuantity() < 0){
            throw new SystemException("Quantity must be greater than 0");
        }
        if(!listCartItems.containsKey(cartItem.getProductID())){
            throw new NotFoundException("Product ID is not found!");
        }
        if(cartItem.getQuantity() == 0){
            //remove cart item out of cart
            listCartItems.remove(cartItem.getProductID());
            cartExist.setItems(listCartItems);
            cartExist.setTotalMoney();
            return cartRepository.save(cartExist);
        }
        CartItem cartItemFind =  listCartItems.get(cartItem.getProductID());
        listCartItems.remove(cartItem.getProductID());
        cartItemFind.setQuantity(cartItem.getQuantity());
        listCartItems.put(cartItemFind.getProductID(),cartItemFind);
        cartExist.setItems(listCartItems);
        cartExist.setTotalMoney();
        return cartRepository.save(cartExist);
    }

    @Override
    public Cart findCart(String access_token) {
        return cartRepository.findCartAccess_token(access_token);
    }

    @Transactional
    @Override
    public void removeItem(String access_token, Integer productID) {
        //get cart exist
        Cart cartExist = cartRepository.findCartAccess_token(access_token);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        Map<Integer,CartItem> listCartItems = cartExist.getItems();
        //check cart item exist
        if(listCartItems.get(productID) == null){
            throw new NotFoundException("Cart Item Not Exist");
        }
        //remove cart item out of list cart item
        for (Map.Entry<Integer, CartItem> set : cartExist.getItems().entrySet()) {
            // Printing all elements of a Map
            System.out.println(set.getKey());
        }
        System.out.println("---------------");
        listCartItems.remove(productID);
        cartExist.setItems(listCartItems);
        //remove cart item by product id
        cartItemRepository.deleteCartItemByProductID(productID);
        //update total money
        cartExist.setTotalMoney();
        //save into database
        cartRepository.save(cartExist);
    }

    @Override
    public void clear(String access_token) {
        //check cart exist
        Cart cartExist = cartRepository.findCartAccess_token(access_token);
        if(cartExist == null){
            throw new NotFoundException("Cart is not found!");
        }
        //remove cart
        cartRepository.deleteById(cartExist.getId());
    }
}
