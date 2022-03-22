package com.example.payment.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentDto implements Serializable {
    private Long orderID;
    private String accountID;
    private String paymentStatus;
    private String message;

    public PaymentDto(long orderID,String accountID){
        this.orderID = orderID;
        this.accountID = accountID;
    }
}
