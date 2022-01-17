package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    void save(Order order);
    Order getOrderById(Integer id);

}
