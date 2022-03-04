package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "order_details")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailKey orderDetailKey;
    //create relationship between order
    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @Column(name = "product_id",updatable = false,insertable = false)
    private long productID;

    private int quantity;

    private BigDecimal unitPrice;

    @Override
    public String toString() {
        return "quantity "+quantity;
    }
}
