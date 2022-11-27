package com.github.alanpryoga.springewalletapp.walletservice.repository.db;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;

import java.util.List;

public interface UserDbRepository {

    List<User> list(UserType userType);

    User detail(int userId);

    int create(User user);

}
