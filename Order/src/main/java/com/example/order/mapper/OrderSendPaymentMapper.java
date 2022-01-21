package com.example.order.mapper;

import com.example.order.dto.OrderSendPayment;
import com.example.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderSendPaymentMapper {
    OrderSendPaymentMapper INSTANCE = Mappers.getMapper(OrderSendPaymentMapper.class);
    OrderSendPayment orderSendPaymentDTO(Order order);
}
