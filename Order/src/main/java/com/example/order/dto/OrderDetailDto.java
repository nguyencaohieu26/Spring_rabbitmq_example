package com.example.order.dto;

import com.example.order.entity.OrderDetail;
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

    public OrderDetailDto(OrderDetail orderDetail){
        this.orderID    = orderDetail.getOrderDetailKey().getOrderID();
        this.productID  = orderDetail.getProductID();
        this.unitPrice  = orderDetail.getUnitPrice();
        this.quantity   = orderDetail.getQuantity();
    }
}
