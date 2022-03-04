package com.example.order.enums;

public enum Status {
    ACTIVE,DELETE;

    public enum InventoryStatus{
        PENDING,OUT_OF_STOCK,DONE,RETURNED,RETURN,FAIL
    }
    public enum PaymentStatus{
        PENDING,PAID,UNPAID,REFUND,REFUNDED
    }
    public enum OrderStatus{
        PENDING,CONFIRM,REJECT,DONE,DELETED
    }
    public static class OrderMessage{
        public static String NOT_ENOUGH_BALANCE = "Balance is not enough!";
        public static String NOT_FOUND_USER     = "Can't found user!";
        public static String NOT_FOUND_WALLET   = "Wallet is not found!";
    }
}
