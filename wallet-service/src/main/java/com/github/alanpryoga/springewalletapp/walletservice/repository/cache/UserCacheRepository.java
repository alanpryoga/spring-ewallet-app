package com.github.alanpryoga.springewalletapp.walletservice.repository.cache;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;

public interface UserCacheRepository {

    User detail(int userId);

    void save(User user);

}
