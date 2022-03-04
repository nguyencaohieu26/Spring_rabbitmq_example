package com.example.payment.service.transaction;

import com.example.payment.entity.TransactionHistory;
import com.example.payment.entity.Wallet;

import java.util.List;

public interface TransactionService {
    public List<TransactionHistory> getAll();
}
