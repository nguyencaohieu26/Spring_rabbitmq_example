package com.example.inventory.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto implements Serializable {
    private long orderID;
    private long productID;
    private BigDecimal unitPrice;
    private int quantity;
}
