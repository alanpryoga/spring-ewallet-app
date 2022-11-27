package com.github.alanpryoga.springewalletapp.walletservice.service;

public interface TransactionService {

    int topUp(int amount, int userId);

    int payment(int amount, int customerId, int merchantId);

}
