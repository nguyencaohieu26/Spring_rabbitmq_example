package com.example.payment.service;

import com.example.payment.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();
    Account save(Account account);
    int updateBalance(Integer id,Integer amount);
}
