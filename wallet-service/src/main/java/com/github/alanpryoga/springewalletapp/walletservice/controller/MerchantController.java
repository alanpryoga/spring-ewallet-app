package com.github.alanpryoga.springewalletapp.walletservice.controller;

import com.github.alanpryoga.springewalletapp.walletservice.entity.User;
import com.github.alanpryoga.springewalletapp.walletservice.entity.UserType;
import com.github.alanpryoga.springewalletapp.walletservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Handle HTTP request for get list of merchant.
     *
     * @return
     */
    @RequestMapping(value = "/merchant/list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> list(@RequestParam("skip") Optional<Integer> skip, @RequestParam("limit") Optional<Integer> limit) {
        Map<String, Object> result = new HashMap<>();

        int skipRows = skip.orElse(0);
        int limitRows = limit.orElse(10);

        result.put("status", "ok");
        result.put("message", "success");

        List<User> listMerchant = userService.list(UserType.MERCHANT)
                .stream()
                .skip(skipRows)
                .limit(limitRows)
                .collect(Collectors.toList());;

        result.put("data", listMerchant);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Handle HTTP request for get detail of merchant
     *
     * @return
     */
    @RequestMapping(value = "/merchant/detail", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> detail(@RequestParam(name = "merchantId") int merchantId) {
        Map<String, Object> result = new HashMap<>();

        User user = userService.detail(merchantId);
        if (user.getName() == null || user.getName().equals("")) {
            result.put("status", "error");
            result.put("message", "failed to get merchant detail");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            if (user.getUserType() != UserType.MERCHANT) {
                result.put("status", "error");
                result.put("message", "failed to get merchant detail");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            } else {
                result.put("status", "ok");
                result.put("message", "success");
                result.put("data", user);

                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
    }

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
