package com.example.order.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderSendPayment implements Serializable {
    private Integer id;
    private boolean check_out;
    private int totalPrice;
    private int account_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCheck_out() {
        return check_out;
    }

    public void setCheck_out(boolean check_out) {
        this.check_out = check_out;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
