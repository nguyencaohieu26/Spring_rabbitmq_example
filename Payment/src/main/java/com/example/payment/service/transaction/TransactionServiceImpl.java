package com.example.payment.service.transaction;

import com.example.payment.entity.TransactionHistory;
import com.example.payment.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionHistory> getAll() {
        return transactionHistoryRepository.findAll();
    }
}
