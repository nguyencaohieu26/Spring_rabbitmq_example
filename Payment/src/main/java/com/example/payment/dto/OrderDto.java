package com.example.payment.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private long orderID;
    private long accountID;
    private BigDecimal totalPrice;
    private String paymentStatus;
    private String inventoryStatus;
    private String orderStatus;
    private String message;
    private Set<OrderDetailDto> orderDetails = new HashSet<>();
}
