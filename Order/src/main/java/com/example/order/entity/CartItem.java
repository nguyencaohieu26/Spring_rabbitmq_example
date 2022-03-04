package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

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
    private Long id;

    @NotNull(message = "Product id is required")
    @Column(name = "product_id")
    private Long productID;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product image is required")
    private String thumbnail;

    @NotNull(message = "Price is required")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false,updatable = false)
    @JsonIgnore
    private Cart cart;
}
