package com.yzm.springCloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentZKController {

    @Value("${server.port}")
    private String serverPort;

    private RestTemplate restTemplate;

    @GetMapping(value = "payment/zk")
    public String getInfo() {
        return "springCloud with zookeeper: " + serverPort + "\t" + UUID.randomUUID().toString();

    }


}
