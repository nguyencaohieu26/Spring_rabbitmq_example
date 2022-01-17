package com.example.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private Integer account_id;
    private boolean check_out;
    private int totalPrice;
}
