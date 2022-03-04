package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String access_token;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @JsonIgnore
    private Map<Long,CartItem> items;

    @Override
    public String toString() {
        return new Gson().toJson(items);
    }

    //METHOD
    public List<CartItem> getListCartItems(){
        return new ArrayList<>(this.items.values());
    }

    @JsonIgnore
    public BigDecimal getTotal(){
        List<CartItem> list = this.getListCartItems();
        BigDecimal total = new BigDecimal(0);
        for(CartItem cartitem : list){
            total =  total.add(BigDecimal.valueOf(cartitem.getQuantity()).multiply(cartitem.getUnitPrice()));
        }
        return total;
    }

    @JsonIgnore
    public void setTotalMoney(){
        this.totalPrice = getTotal();
    }
}
