package com.example.order.service;

import com.example.order.dto.CartToOrderDTO;
import com.example.order.dto.OrderDTO;
import com.example.order.entity.CartItem;
import com.example.order.entity.Order;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.OrderDetailKey;
import com.example.order.enums.SearchOperation;
import com.example.order.enums.Status;
import com.example.order.exceptions.NotFoundException;
import com.example.order.exceptions.SystemException;
import com.example.order.queue.Config;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.specification.FilterField;
import com.example.order.specification.OrderSpecification;
import com.example.order.specification.SearchCriteria;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    RabbitTemplate template;

    @Override
    public Page<Order> getOrders(FilterField field) {
        return null;
    }

    @Override
    public void save(CartToOrderDTO cart) {
        if(cart == null){
            throw new NotFoundException("Cart is not found!");
        }
            Order order = Order.builder()
                    .accountID(cart.getAccountID())
                    .totalPrice(cart.getTotalPrice())
                    .build();
            orderRepository.save(order);
            Set<OrderDetail> orderDetailsList = new HashSet<>();
            for (CartItem cartitem:cart.getListCartItems()){
                OrderDetail newOrderDetail = OrderDetail.builder()
                        .orderDetailKey(new OrderDetailKey(order.getId(),cartitem.getProductID()))
                        .quantity(cartitem.getQuantity())
                        .unitPrice(cartitem.getUnitPrice())
                        .order(order)
                        .productID(cartitem.getProductID())
                        .build();
                orderDetailsList.add(newOrderDetail);
            }
                order.setOrderDetails(orderDetailsList);
                order.setInventory_status(Status.InventoryStatus.PENDING.name());
                order.setPayment_status(Status.PaymentStatus.PENDING.name());
                orderRepository.save(order);
            try{
                OrderDTO newOrderDto = new OrderDTO(order);
                template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_INVENTORY,newOrderDto);
                template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_PAYMENT,newOrderDto);
            }catch (Exception e){
                throw new SystemException("Error occur!");
            }
    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public void update(Order order) {}
}
