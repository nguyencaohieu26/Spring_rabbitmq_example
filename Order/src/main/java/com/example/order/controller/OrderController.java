package com.example.order.controller;

import com.example.order.dto.OrderDTO;
import com.example.order.dto.OrderSendPayment;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.queue.MessageConfig;
import com.example.order.response.RestPagination;
import com.example.order.response.RestResponse;
import com.example.order.service.OrderService;
import com.example.order.specification.FilterField;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {


    public static Order orderSave = new Order();

    @Autowired
    private OrderService orderService;


    //Create order
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        //save to static variable
        order.calculateTotalPrice();
        orderSave = order;
        orderService.save(orderSave);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    //Get list order
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getListOrder(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "pageSize",defaultValue = "6") int pageSize,
            @RequestParam(name = "minPrice",defaultValue = "-1") int minPrice,
            @RequestParam(name = "maxPrice",defaultValue = "-1") int maxPrice,
            @RequestParam(name = "id",defaultValue = "-1") int id,
            @RequestParam(name = "account_id",defaultValue = "-1") int account_id
    ){
        //Create Filter field
        FilterField orderFilter = FilterField.FilterFieldBuilder.aFilterField()
                .withMinPrice(minPrice)
                .withMaxPrice(maxPrice)
                .withId(id)
                .withAccountId(account_id)
                .withPage(page)
                .withPageSize(pageSize)
                .build();
        Page<Order> orderPage = orderService.getOrders(orderFilter);
        if(orderPage.getContent().size() == 0){
            return new ResponseEntity<>(
                    new RestResponse.Success()
                            .setMessage("List is empty")
                            .build()
                    ,HttpStatus.OK);
        }
        //Convert List to OrderDTO
        List<OrderDTO> listOrderDTO = orderPage.getContent().stream().map(OrderMapper.INSTANCE::orderToOrderDTO).collect(Collectors.toList());
        return new ResponseEntity<>(
                new RestResponse.Success()
                        .addDatas(listOrderDTO).setPagination(
                                new RestPagination(orderPage.getNumber() + 1,orderPage.getSize(),orderPage.getTotalElements()))
                        .build()
                ,HttpStatus.OK);
    }

    //get order by id
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id){
        return new ResponseEntity<>(
                new RestResponse.Success().addData(orderService.getOrderById(id))
                        .build()
                ,HttpStatus.OK);
    }

    //handle response by Payment Service from RabbitMQ
    public void handlerMessageFromQueue(OrderSendPayment resp){
        if(resp.isCheck_out()){
            System.out.println("Order Successfully");
            Order ord = orderService.getOrderById(resp.getId());
            ord.setCheck_out(true);
            System.out.println(ord.isCheck_out());
            orderService.update(ord);
        }else{
            System.out.println("Order Fall");
        }
    }
}
