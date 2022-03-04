package com.example.order.service;

import com.example.order.dto.CartToOrderDTO;
import com.example.order.entity.Order;
import com.example.order.specification.FilterField;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<Order> getOrders(FilterField field);
    void save(CartToOrderDTO cart);
    Order getOrderById(Long id);
    void update(Order order);
}
