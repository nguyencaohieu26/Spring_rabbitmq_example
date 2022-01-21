package com.example.order.service;

import com.example.order.entity.Order;
import com.example.order.specification.FilterField;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<Order> getOrders(FilterField field);
    void save(Order order);
    Order getOrderById(Integer id);
    void update(Order order);
}
