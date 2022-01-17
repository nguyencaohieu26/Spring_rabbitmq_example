package com.example.order.dto;

import com.example.order.entity.OrderDetail;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderDTO {
    private Integer id;
    private String account_name;
    private String isCheckOut;
    private int totalPrice;
    private LocalDateTime createdAt;
    Set<OrderDetail> orderDetails;
}
