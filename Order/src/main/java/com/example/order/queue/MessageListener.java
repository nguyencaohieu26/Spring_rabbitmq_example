package com.example.order.queue;

import com.example.order.controller.OrderController;
import com.example.order.dto.OrderSendPayment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListener {

    private final OrderController orderController;
    @Autowired
    public MessageListener(OrderController orderController){
        this.orderController = orderController;
    }

    @RabbitListener(queues = MessageConfig.PAYMENT_QUEUE)
    public void listenMessageFromPayment(OrderSendPayment obj){
        orderController.handlerMessageFromQueue(obj);
    }

}
