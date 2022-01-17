package com.example.payment.controller;

import com.example.payment.entity.Account;
import com.example.payment.entity.Order;
import com.example.payment.queue.MessageConfig;
import com.example.payment.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin("*")
public class PaymentController {

    private static Logger logger = Logger.getLogger(PaymentController.class);
    @Autowired
    private AccountService accountService;

    @Autowired
    private RabbitTemplate template;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAccounts(){
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    //create account
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> createAccount(@RequestBody Account account){
        return new ResponseEntity<>(accountService.save(account),HttpStatus.OK);
    }

    //update total price
    public void updateTotal(Order order){
        System.out.println(order.getTotalPrice());
        int status =  accountService.updateBalance(order.getAccount_id(),order.getTotalPrice());
        logger.info("Order Status: "+status);
        template.convertAndSend(MessageConfig.ORDER_EXCHANGE,MessageConfig.PAYMENT_ROUTING_KEY,status);
    }
}
