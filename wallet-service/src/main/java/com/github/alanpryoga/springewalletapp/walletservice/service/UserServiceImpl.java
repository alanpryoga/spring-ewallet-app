package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.repository.db.UserDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl implementation for UserService interface.
 *
 * @author Ade Syahlan Prayoga
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDbRepository userDbRepository;

    /**
     * Get list of user by given user type.
     *
     * @param userType user type
     * @return
     */
    @Override
    public List<User> list(UserType userType) {
        return userDbRepository.list(userType);
    }

    /**
     * Create a new user by given user info.
     *
     * @param user user info
     * @return
     */
    @Override
    public int create(User user) {
        return userDbRepository.create(user);
    }

}
