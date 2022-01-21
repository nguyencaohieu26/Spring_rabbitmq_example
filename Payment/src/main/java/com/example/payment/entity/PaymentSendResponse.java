package com.example.payment.entity;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentSendResponse implements Serializable {
    private Integer id;
    private boolean check_out;
    private int totalPrice;
    private int account_id;
}
