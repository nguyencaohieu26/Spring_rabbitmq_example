package com.example.payment.service.wallet;

import com.example.payment.entity.Wallet;
import com.example.payment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet getWallet(long accountID) {
        return walletRepository.findWalletsByAccountID(accountID);
    }
}
