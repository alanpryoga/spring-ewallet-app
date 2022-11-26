package com.github.alanpryoga.springewalletapp.walletservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * HealthController handle HTTP request for health related endpoints.
 *
 * @author Ade Syahlan Prayoga
 */
@RestController
public class HealthController {

    @RequestMapping(value = "/health/check", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> check() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ok");
        result.put("message", "service is healthy");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}