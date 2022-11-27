package com.github.alanpryoga.springewalletapp.walletservice.repository.cache;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserCacheRepositoryImpl implements UserCacheRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public User detail(int userId) {
        String key = "user:" + userId;

        String name = (String) redisTemplate.opsForHash().get(key, "name");
        String phone = (String) redisTemplate.opsForHash().get(key, "phone");
        String userTypeStr = (String) redisTemplate.opsForHash().get(key, "user_type");

        UserType userType = UserType.CUSTOMER;
        if (userTypeStr != null && userTypeStr.equals("merchant")) {
            userType = UserType.MERCHANT;
        }

        return new User(userId, name, phone, userType);
    }

    @Override
    public void save(User user) {
        String key = "user:" + user.getId();

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("name", user.getName());
        props.put("phone", user.getPhone());
        String userTypeStr = "customer";
        if (user.getUserType() == UserType.MERCHANT) {
            userTypeStr = "merchant";
        }
        props.put("user_type", userTypeStr);

        redisTemplate.opsForHash().putAll(key, props);
    }

}
