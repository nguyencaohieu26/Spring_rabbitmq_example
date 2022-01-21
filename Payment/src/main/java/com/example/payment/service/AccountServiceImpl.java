package com.example.payment.service;

import com.example.payment.entity.Account;
import com.example.payment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public int updateBalance(Integer id,Integer amount) {
        Optional<Account> accountExist = accountRepository.findById(id);
        if(!accountExist.isPresent()){
            throw new NullPointerException("Account Is Not Found!");
        } else {
            int amountExist = accountExist.get().getBalance() - amount;
            if(amountExist < 0){
                return 0;
            }else{
                accountExist.get().setBalance(amountExist);
                accountRepository.save(accountExist.get());
                return 1;
            }
        }
    }
}
