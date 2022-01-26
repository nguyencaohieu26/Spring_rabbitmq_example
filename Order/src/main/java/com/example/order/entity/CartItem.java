package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cart_items")
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer productID;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product image is required")
    private String thumbnail;

    private int unitPrice;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false,updatable = false)
    @JsonIgnore
    private Cart cart;
}
