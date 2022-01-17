package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    //create relationship between product
    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    //create relationship between order
    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private int quantity;

    @Column(name = "unit_price")
    private int unitPrice;

    @Override
    public String toString() {
        return "quantity "+quantity;
    }
}
