package com.example.order.dto;

import com.example.order.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartToOrderDTO implements Serializable {
    private String access_token;
    private Long id;
    private List<CartItem> listCartItems;
    private BigDecimal totalPrice;
}
