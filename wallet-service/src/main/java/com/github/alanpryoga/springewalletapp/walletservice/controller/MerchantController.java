package com.github.alanpryoga.springewalletapp.walletservice.controller;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * MerchantController handle HTTP requests for merchant related.
 *
 * @author Ade Syahlan Prayoga
 */
@RestController
public class MerchantController {

    @Autowired
    private UserService userService;

    /**
     * Handle HTTP request for register new merchant.
     *
     * @param user merchant info
     * @return
     */
    @RequestMapping(value = "/merchant/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // Set user type as merchant
        user.setUserType(UserType.MERCHANT);

        int customerId = userService.create(user);
        if (customerId == 0) {
            result.put("status", "error");
            result.put("message", "failed to register new merchant");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        } else {
            result.put("status", "ok");
            result.put("message", "success to register new merchant");

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

}
