package com.example.order.controller;

import com.example.order.dto.CartToOrderDTO;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public ResponseEntity<?> createOrder(@RequestBody CartToOrderDTO cart){
        orderService.save(cart);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET,value = "/list")
//    public ResponseEntity<?> getListOrder(
//            @RequestParam(name = "page",defaultValue = "1") int page,
//            @RequestParam(name = "pageSize",defaultValue = "6") int pageSize,
//            @RequestParam(name = "minPrice",defaultValue = "-1") int minPrice,
//            @RequestParam(name = "maxPrice",defaultValue = "-1") int maxPrice,
//            @RequestParam(name = "id",defaultValue = "-1") int id,
//            @RequestParam(name = "account_id",defaultValue = "-1") int account_id
//    ){
//        //Create Filter field
//        FilterField orderFilter = FilterField.FilterFieldBuilder.aFilterField()
//                .withMinPrice(minPrice)
//                .withMaxPrice(maxPrice)
//                .withId(id)
//                .withAccountId(account_id)
//                .withPage(page)
//                .withPageSize(pageSize)
//                .build();
//        Page<Order> orderPage = orderService.getOrders(orderFilter);
//        if(orderPage.getContent().size() == 0){
//            return new ResponseEntity<>(
//                    new RestResponse.Success()
//                            .setMessage("List is empty")
//                            .build()
//                    ,HttpStatus.OK);
//        }
//        //Convert List to OrderDTO
//        List<OrderDTO> listOrderDTO = orderPage.getContent().stream().map(OrderMapper.INSTANCE::orderToOrderDTO).collect(Collectors.toList());
//        return new ResponseEntity<>(
//                new RestResponse.Success()
//                        .addDatas(listOrderDTO).setPagination(
//                                new RestPagination(orderPage.getNumber() + 1,orderPage.getSize(),orderPage.getTotalElements()))
//                        .build()
//                ,HttpStatus.OK);
//    }

//    @RequestMapping(method = RequestMethod.GET,value = "/get/{id}")
//    public ResponseEntity<?> getOrder(@PathVariable Long id){
//        return new ResponseEntity<>(
//                new RestResponse.Success().addData(orderService.getOrderById(id)).build(),HttpStatus.OK);
//    }

}
