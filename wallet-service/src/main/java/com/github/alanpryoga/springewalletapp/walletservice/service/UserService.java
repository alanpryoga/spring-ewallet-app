package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;

import java.util.List;

public interface UserService {

    List<User> list(UserType userType);

    User detail(int userId);

    int create(User user);

}
