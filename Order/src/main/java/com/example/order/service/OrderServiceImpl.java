package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.OrderDetailKey;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Order> getOrders() {

        return orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        Order convertOrder = Order.builder()
                .account_id(order.getAccount_id())
                .totalPrice(order.getTotalPrice())
                .build();
        //save order
        orderRepository.save(convertOrder);
        //
        Set<OrderDetail> listDetails = new HashSet<>();
        for(OrderDetail od:order.getOrderDetails()){
            OrderDetail convertOrderDetail = OrderDetail.builder()
                    .orderDetailKey(new OrderDetailKey(convertOrder.getId(),od.getOrderDetailKey().getProductID()))
                    .quantity(od.getQuantity())
                    .unitPrice(od.getUnitPrice())
                    .product(productRepository.findById(od.getOrderDetailKey().getProductID()).get())
                    .order(convertOrder)
                    .build();
            //create order detail
            listDetails.add(convertOrderDetail);
            orderDetailRepository.save(convertOrderDetail);
            orderRepository.save(convertOrder);
        }
        convertOrder.setOrderDetails(listDetails);
    }

    @Override
    public Order getOrderById(Integer id) {
        if(orderRepository.findById(id).isPresent()){
            return orderRepository.findById(id).get();
        }else{
            throw new NullPointerException("Order Is Not Found!");
        }
    }
}
