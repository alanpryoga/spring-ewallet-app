package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

public interface TransactionDbRepository {

    void topUp(int amount, int userId) throws Exception;

    void payment(int amount, int customerId, int merchantId) throws Exception;

}
