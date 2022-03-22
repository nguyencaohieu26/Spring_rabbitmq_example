package com.example.payment.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity(name="transaction_histories")
public class TransactionHistory extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Sender is required!")
    private String senderID;

    @NotNull(message = "Receiver is required")
    private  String receiverID;

    private Long orderID;
    private String paymentType;

    @NotNull(message = "Amount is required!")
    private BigDecimal amount;

    private String status;
    private String message;
}
