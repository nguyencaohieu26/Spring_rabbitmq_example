package com.example.payment.queue;

import com.example.payment.dto.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage {
    @Autowired
    ConsumerService consumerService;

    @RabbitListener(queues = Config.QUEUE_PAYMENT)
    public void getOrder(OrderDto orderDto){
        System.out.println("----- Received Order ------");
        consumerService.handlePayment(orderDto);
        System.out.println("----- Done Processing Order -------");
    }
}
