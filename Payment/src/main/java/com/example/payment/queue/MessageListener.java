package com.example.payment.queue;

import com.example.payment.controller.PaymentController;
import com.example.payment.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListener {

    @Autowired
    PaymentController paymentController;

    @RabbitListener(queues = MessageConfig.ORDER_QUEUE)
    public void listenMessageFromOrder(Order order){
        paymentController.updateTotal(order);
    }
}
