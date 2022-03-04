package com.example.inventory.queue;

import com.example.inventory.dto.OrderDetailDto;
import com.example.inventory.dto.OrderDto;
import com.example.inventory.entity.ImportExportHistory;
import com.example.inventory.entity.Product;
import com.example.inventory.enums.Status;
import com.example.inventory.exceptions.SystemException;
import com.example.inventory.repository.ImportExportHistoryRepository;
import com.example.inventory.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ConsumerService {

    @Autowired
    private ImportExportHistoryRepository importExportHistoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void handleMessage(OrderDto orderDto){
        if(orderDto.getInventoryStatus().equals(Status.InventoryStatus.PENDING.name())){
            handlePending(orderDto);
        }
        if(orderDto.getInventoryStatus().equals(Status.InventoryStatus.RETURN.name())){
            handleReturn(orderDto);
        }
    }

    @Transactional
    public void handlePending(OrderDto orderDto){
        Set<OrderDetailDto> orderDetailDtoList = orderDto.getOrderDetails();
        List<Product> products = new ArrayList<>();
        List<ImportExportHistory> listExportHistory = new ArrayList<>();
        for (OrderDetailDto order_detail:orderDetailDtoList){
            Optional<Product> productExist = productRepository.findById(order_detail.getProductID());
            if(!productExist.isPresent()){return;}
            ImportExportHistory history = ImportExportHistory.builder()
                    .orderID(orderDto.getOrderID())
                    .type(Status.HistoryType.EXPORT.name())
                    .productID(order_detail.getProductID())
                    .quantity(order_detail.getQuantity())
                    .build();
            if(order_detail.getQuantity() > productExist.get().getInStock()){
                System.out.println("---- Handler Not Enough Product In Stock -----");
                orderDto.setMessage("Product in inventory is not enough");
                orderDto.setInventoryStatus(Status.InventoryStatus.OUT_OF_STOCK.name());
                orderDto.setOrderStatus(Status.OrderStatus.REJECT.name());
                rabbitTemplate.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
                return;
            }
            productExist.get().setInStock(productExist.get().getInStock() - order_detail.getQuantity());
            products.add(productExist.get());
            listExportHistory.add(history);
        }
        try{
            System.out.println("---- Handler Enough Product In Stock -----");
            productRepository.saveAll(products);
            importExportHistoryRepository.saveAll(listExportHistory);
            orderDto.setOrderStatus(Status.OrderStatus.CONFIRM.name());
            orderDto.setInventoryStatus(Status.InventoryStatus.DONE.name());
            rabbitTemplate.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
        }catch (Exception e){
            throw new SystemException("Error occur!");
        }
    }
    @Transactional
    public void handleReturn(OrderDto orderDto){
        Set<OrderDetailDto> orderDetailDtoList = orderDto.getOrderDetails();
        List<Product> products = new ArrayList<>();
        List<ImportExportHistory> listExportHistory = new ArrayList<>();
        for(OrderDetailDto order_detail:orderDetailDtoList){
            Optional<Product> productExist = productRepository.findById(order_detail.getProductID());
            if(!productExist.isPresent()){return;}
            ImportExportHistory history = ImportExportHistory.builder()
                    .orderID(orderDto.getOrderID())
                    .type(Status.HistoryType.IMPORT.name())
                    .productID(order_detail.getProductID())
                    .quantity(order_detail.getQuantity())
                    .build();
            productExist.get().setInStock(productExist.get().getInStock() + order_detail.getQuantity());
            products.add(productExist.get());
            listExportHistory.add(history);
        }
        try{
            System.out.println("----- Handler Return Product Success -------");
            importExportHistoryRepository.saveAll(listExportHistory);
            productRepository.saveAll(products);
            orderDto.setInventoryStatus(Status.InventoryStatus.RETURNED.name());
            orderDto.setOrderStatus(Status.OrderStatus.DONE.name());
            orderDto.setMessage("Return product success");
            rabbitTemplate.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
        }catch (Exception e){
            throw new SystemException("Error occurs");
        }
    }

}
