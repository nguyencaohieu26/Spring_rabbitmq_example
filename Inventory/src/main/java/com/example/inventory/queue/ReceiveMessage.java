package com.example.inventory.queue;

import com.example.inventory.dto.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage {
    @Autowired
    ConsumerService service;

    @RabbitListener(queues = Config.QUEUE_INVENTORY)
    public void getOrder(OrderDto orderDto){
        System.out.println("----- Receive Order For Processing ------");
        service.handleMessage(orderDto);
        System.out.println("----- Inventory processed order -----");
    }
}
