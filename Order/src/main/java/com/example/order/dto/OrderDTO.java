package com.example.order.dto;

import com.example.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private long orderID;
    private String accountID;
    private BigDecimal totalPrice;
    private String paymentStatus;
    private String inventoryStatus;
    private String orderStatus;
    private String message;
    private Set<OrderDetailDto> orderDetails = new HashSet<>();

    public OrderDTO(Order order){
        this.orderID    = order.getId();
        this.accountID  = order.getAccountID();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getStatus();
        this.paymentStatus = order.getPayment_status();
        this.inventoryStatus = order.getInventory_status();
        order.getOrderDetails().forEach(orderDetail -> {
            this.orderDetails.add(new OrderDetailDto(orderDetail));
        });
    }
}
