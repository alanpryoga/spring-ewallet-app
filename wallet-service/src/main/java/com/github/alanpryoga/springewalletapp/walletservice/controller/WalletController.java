package com.github.alanpryoga.springewalletapp.walletservice.controller;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WalletController handle HTTP request for wallet related endpoints.
 *
 * @author Ade Syahlan Prayoga
 */
@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * Handle HTTP request for get wallet balance info.
     *
     * @return
     */
    @RequestMapping(value = "wallet/balance", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> balance(@RequestParam(name = "userId") int userId) {
        Map<String, Object> result = new HashMap<>();

        result.put("status", "ok");
        result.put("message", "success");

        int balance = walletService.balance(userId);
        result.put("data", balance);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
