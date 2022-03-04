package com.example.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private long orderID;
    private long productID;
    private BigDecimal unitPrice;
    private int quantity;
}
