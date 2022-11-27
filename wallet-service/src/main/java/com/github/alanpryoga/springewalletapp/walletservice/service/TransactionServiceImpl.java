package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.repository.db.TransactionDbRepository;
import com.github.alanpryoga.springewalletapp.walletservice.repository.db.UserDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserDbRepository userDbRepository;

    @Autowired
    private TransactionDbRepository transactionDbRepository;

    @Override
    public int topUp(int amount, int userId) {
        try {
            transactionDbRepository.topUp(amount, userId);

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int payment(int amount, int customerId, int merchantId) {
        // Validate if customerId user type is customer
        User customer = userDbRepository.detail(customerId);
        if (customer == null || customer.getUserType() != UserType.CUSTOMER) {
            return 0;
        }

        // Validate if merchantId user type is merchant
        User merchant = userDbRepository.detail(merchantId);
        if (merchant == null || merchant.getUserType() != UserType.MERCHANT) {
            return 0;
        }

        try {
            transactionDbRepository.payment(amount, customerId, merchantId);

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
