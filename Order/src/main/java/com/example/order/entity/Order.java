package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="orders")
@SQLDelete(sql = "UPDATE accounts SET deleted = true WHERE id=?") @Where(clause = "deleted = false")
public class Order extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Customer ID is required")
    @Column(name = "account_id")
    private Integer account_id;

    @Column(name = "check_out")
    private boolean check_out = Boolean.FALSE;

    @Column(name = "total_price")
    private int totalPrice;

    private boolean deleted = Boolean.FALSE;

    //1 order have many order detail
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<OrderDetail> orderDetails = new HashSet<>();

    //method count totalPrice
    public void calculateTotalPrice(){
        int total = 0;
        for(OrderDetail od : this.orderDetails){
            total += od.getUnitPrice()*od.getQuantity();
        }
        this.totalPrice = total;
    }
}
