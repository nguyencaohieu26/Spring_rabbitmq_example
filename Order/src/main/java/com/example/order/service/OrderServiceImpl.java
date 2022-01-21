package com.example.order.service;

import com.example.order.entity.Order;
import com.example.order.entity.OrderDetail;
import com.example.order.entity.OrderDetailKey;
import com.example.order.enums.SearchOperation;
import com.example.order.mapper.OrderSendPaymentMapper;
import com.example.order.queue.MessageConfig;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
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
    ProductRepository productRepository;

    @Autowired
    private RabbitTemplate template;

    @Override
    public Page<Order> getOrders(FilterField field) {
        Specification<Order> spe = Specification.where(null);
        PageRequest paging = PageRequest.of(field.getPage() - 1,field.getPageSize());
        //filter by id
        if(field.getId() > 0){
            spe = spe.and(new OrderSpecification(new SearchCriteria(FilterField.ID, SearchOperation.EQUALITY,field.getId())));
        }
        //filter by account id
        if(field.getAccountId() > 0){
            spe = spe.and(new OrderSpecification(new SearchCriteria(FilterField.ACCOUNT_ID,SearchOperation.EQUALITY,field.getAccountId())));
        }
        //filter by max price
        if(field.getMaxPrice() > 0){
            spe = spe.and(new OrderSpecification(new SearchCriteria(FilterField.PRICE,SearchOperation.LESS_THAN,field.getMaxPrice())));
            System.out.println(field.getMaxPrice());
        }
        //filter by min price
        if(field.getMinPrice() > 0){
            System.out.println(field.getMinPrice());
            spe = spe.and(new OrderSpecification(new SearchCriteria(FilterField.PRICE,SearchOperation.GREATER_THAN,field.getMinPrice())));
        }
        return orderRepository.findAll(spe,paging);
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
        //send message to queue
        template.convertAndSend(MessageConfig.ORDER_EXCHANGE, MessageConfig.ORDER_ROUTING_KEY, OrderSendPaymentMapper.INSTANCE.orderSendPaymentDTO(convertOrder));
    }

    @Override
    public Order getOrderById(Integer id) {
        if(orderRepository.findById(id).isPresent()){
            return orderRepository.findById(id).get();
        }else{
            throw new NullPointerException("Order Is Not Found!");
        }
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

}
