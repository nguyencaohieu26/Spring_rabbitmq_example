package com.example.inventory.enums;

public enum Status {
    ACTIVE,INACTIVE;

    public enum InventoryStatus {PENDING,OUT_OF_STOCK,DONE,FAIL,RETURNED,RETURN}
    public enum PaymentStatus {PAID, UNPAID, REFUND, REFUNDED}

    public enum HistoryType {IMPORT,EXPORT}

    public enum OrderStatus {PENDING,CONFIRM,REJECT,DONE,DELETED}
    public static class OrderMessage{
        public static String NOT_ENOUGH_BALANCE = "Balance is not enough";
        public static String NOT_FOUND_USER = "Can not found user";
        public static String NOT_FOUND_WALLET = "Wallet not found";
    }
}
