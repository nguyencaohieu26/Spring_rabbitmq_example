package com.example.order.queue;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.enums.Status;
import com.example.order.exceptions.SystemException;
import com.example.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class ConsumerService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RabbitTemplate template;

    /* Handle message from queue
    *
    * */
    @Transactional
    public void handleMessage(OrderDTO orderDTO){
        Optional<Order> orderExist = orderRepository.findById(orderDTO.getOrderID());
        if(!orderExist.isPresent()){return;}

        if(!orderDTO.getInventoryStatus().equals(Status.InventoryStatus.PENDING.name())){
            orderExist.get().setInventory_status(orderDTO.getInventoryStatus());
        }
        if(!orderDTO.getPaymentStatus().equals(Status.PaymentStatus.PENDING.name())){
            orderExist.get().setPayment_status(orderDTO.getPaymentStatus());
        }
        handleQueue(orderDTO);

    }

    @Transactional
    public void handleQueue(OrderDTO orderDTO){
        Order order = orderRepository.getById(orderDTO.getOrderID());
        if(
            order.getPayment_status().equals(Status.PaymentStatus.UNPAID.name())
            && order.getInventory_status().equals(Status.InventoryStatus.DONE.name())
        ){
            orderDTO.setInventoryStatus(Status.InventoryStatus.RETURN.name());
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_INVENTORY,orderDTO);
        }
        if(
            order.getInventory_status().equals(Status.InventoryStatus.OUT_OF_STOCK.name())
            && order.getPayment_status().equals(Status.PaymentStatus.PAID.name())
        ){
            try {
                orderDTO.setPaymentStatus(Status.PaymentStatus.REFUND.name());
                template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_PAYMENT,orderDTO);
            }catch (Exception e){
                throw new SystemException("Error occurs");
            }
        }
    }

}
