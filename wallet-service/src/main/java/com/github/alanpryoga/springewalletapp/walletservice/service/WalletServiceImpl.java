package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.repository.db.WalletDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDbRepository walletDbRepository;

    /**
     * Get wallet balance info by given user id.
     *
     * @param userId user id
     * @return
     */
    @Override
    public int balance(int userId) {
        return walletDbRepository.balance(userId);
    }

}
