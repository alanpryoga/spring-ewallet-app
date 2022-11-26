package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;

public interface UserDbRepository {

    int create(User user);

}
