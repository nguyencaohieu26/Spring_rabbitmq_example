package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.queue.MessageConfig;
import com.example.order.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {

    private static Logger logger = Logger.getLogger(OrderController.class);

    public static Order orderSave = new Order();

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate template;

    //Create order
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        //save to static variable
        order.calculateTotalPrice();
        orderSave = order;
        //send message to queue
        template.convertAndSend(MessageConfig.ORDER_EXCHANGE, MessageConfig.ORDER_ROUTING_KEY,order);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    //Get list order
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getListOrder(){
        return new ResponseEntity<>(orderService.getOrders(),HttpStatus.OK);
    }

    //get order by id
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id){
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    //handle response by Payment Service from RabbitMQ
    public void handlerMessageFromQueue(String mess){
        //convert status
        int status = Integer.parseInt(mess);
        if(status == 0){
            logger.info("Account Not Enough");
            System.out.println("Account Not Enough");
        }else if(status == -1){
            logger.info("Amount Must Be At Least 50");
            System.out.println("Amount Must Be At Least 50");
        }else{
            //set order check_out true
            orderSave.setCheck_out(true);
            //save order
            orderService.save(orderSave);
            logger.info("Payment Success");
            System.out.println("Successfully");
        }
    }
}
