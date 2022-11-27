package com.github.alanpryoga.springewalletapp.walletservice.controller;

import com.github.alanpryoga.springewalletapp.walletservice.entity.Payment;
import com.github.alanpryoga.springewalletapp.walletservice.entity.TopUp;
import com.github.alanpryoga.springewalletapp.walletservice.service.TransactionService;
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
 * TransactionController handle HTTP request for transaction related endpoints.
 *
 * @author Ade Syahlan Prayoga
 */
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Handle HTTP request for topup transaction.
     *
     * @return
     */
    @RequestMapping(value = "transaction/topup", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> topUp(@RequestBody TopUp topUpParam) {
        Map<String, Object> result = new HashMap<>();

        int amount = topUpParam.getAmount();
        int userId = topUpParam.getUserId();

        if (transactionService.topUp(amount, userId) == 1) {
            result.put("status", "ok");
            result.put("message", "success to topup");

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status", "error");
            result.put("message", "failed to topup");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * Handle HTTP request for payment transaction.
     *
     * @return
     */
    @RequestMapping(value = "transaction/pay", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> pay(@RequestBody Payment paymentParam) {
        Map<String, Object> result = new HashMap<>();

        int amount = paymentParam.getAmount();
        int customerId = paymentParam.getCustomerId();
        int merchantId = paymentParam.getMerchantId();

        if (transactionService.payment(amount, customerId, merchantId) == 1) {
            result.put("status", "ok");
            result.put("message", "success to pay");

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status", "error");
            result.put("message", "failed to pay");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
