package com.nasuf.springcloud.controller;

import com.nasuf.springcloud.entities.Payment;
import com.nasuf.springcloud.entities.CommonResult;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@EnableEurekaClient
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        String url = PAYMENT_URL + "/payment/create";
        log.info("RestTemplate URL: {}", url);
        return restTemplate.postForObject(url, payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        String url = PAYMENT_URL + "/payment/get/" + id;
        log.info("RestTemplate URL: {}", url);
        return restTemplate.getForObject(url, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/entity/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        String url = PAYMENT_URL + "/payment/get/" + id;
        log.info("RestTemplate URL: {}", url);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(url, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "Get Failure.");
        }
    }
}
