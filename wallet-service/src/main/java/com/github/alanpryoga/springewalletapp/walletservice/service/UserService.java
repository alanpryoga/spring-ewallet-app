package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;

import java.util.List;

public interface UserService {

    List<User> list(UserType userType);

    int create(User user);

}
