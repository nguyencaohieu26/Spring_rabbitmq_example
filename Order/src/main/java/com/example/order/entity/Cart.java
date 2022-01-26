package com.example.order.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String access_token;

    private int totalPrice;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private Map<Integer,CartItem> items;

    @Override
    public String toString() {
        return new Gson().toJson(items);
    }

    //METHOD
    //get list cart item
    public List<CartItem> getListCartItems(){
        return new ArrayList<>(items.values());
    }
    //update product in cart
//    public void update(Product product,int quantity){
//        //validate input
//        if(product == null || quantity <= 0){
//            return;
//        }
//        if(items == null){
//            items = new HashMap<>();
//        }
//        //Create new item
//        CartItem item = null;
//        //Add new product in case the product doesn't exist
//        if(items.containsKey(product.getId())){
//            item = items.get(product.getId());
//            item.setQuantity(quantity);
//            items.put(product.getId(),item);
//        }
//    }

    //add product to cart
//    public void add(Product product,int quantity){
//        if(product == null || quantity <= 0){
//           return;
//        }
//        if(items == null){
//            items = new HashMap<>();
//        }
//        //Create new item
//        CartItem item = null;
//        //check product in cart
//        if(items.containsKey(product.getId())){
//            //if cart exist
//            item = items.get(product.getId());
//            //update quantity
//            item.setQuantity(item.getQuantity() + quantity);
//        }else{
//            item = CartItem.builder()
//                    .id(product.getId())
//                    .productName(product.getName())
//                    .thumbnail(product.getThumbnail())
//                    .unitPrice(product.getPrice())
//                    .quantity(quantity)
//                    .build();
//        }
//        items.put(product.getId(),item);
//    }
    //clear cart
//    public void clear(){
//        if(items == null){
//            System.out.println("Cart is empty");
//            return;
//        }
//        items.clear();
//    }
    //remove item out of cart
//    public void remove(int product_id){
//        if(product_id <=0 || items == null){
//            return;
//        }
//        if(!items.containsKey(product_id)){
//            System.out.println("Product id is not found");
//        }
//        items.remove(product_id);
//    }
    //get total price
    public int getTotal(){
        List<CartItem> list = this.getListCartItems();
        int total = 0;
        for(CartItem cartiem : list){
            total +=cartiem.getUnitPrice() * cartiem.getQuantity();
        }
        return total;
    }
    public void setTotalMoney(){
        this.totalPrice = getTotal();
    }
}
