package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.repository.db.UserDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl implementation for UserService interface.
 *
 * @author Ade Syahlan Prayoga
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDbRepository userDbRepository;

    @Override
    public int create(User user) {
        return userDbRepository.create(user);
    }

}
