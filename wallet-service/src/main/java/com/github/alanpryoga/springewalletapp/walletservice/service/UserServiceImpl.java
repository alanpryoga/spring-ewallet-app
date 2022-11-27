package com.github.alanpryoga.springewalletapp.walletservice.service;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.repository.cache.UserCacheRepository;
import com.github.alanpryoga.springewalletapp.walletservice.repository.db.UserDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * UserServiceImpl implementation for UserService interface.
 *
 * @author Ade Syahlan Prayoga
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDbRepository userDbRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

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
     * Get detail of user by given user id.
     *
     * @param userId user id
     * @return
     */
    @Override
    public User detail(int userId) {
        // Try to get from cache
        User user = userCacheRepository.detail(userId);
        if (user.getName() == null || user.getName().equals("")) {
            // Get from db
            user = userDbRepository.detail(userId);

            if (user.getId() != 0) {
                // Save to cache
                userCacheRepository.save(user);
            }
        }

        return user;
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
