package com.example.order.queue;

import com.example.order.dto.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage {
    @Autowired
    ConsumerService consumerService;

    @RabbitListener(queues = Config.QUEUE_ORDER)
    public void getMessage(OrderDTO orderDTO){
        System.out.println("---- Get Message ------");
        consumerService.handleMessage(orderDTO);
        System.out.println("----- Processing Message Done -----");
    }
}
