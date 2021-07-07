package com.nasuf.springcloud.controller;

import com.nasuf.springcloud.entities.CommonResult;
import com.nasuf.springcloud.entities.Payment;
import com.nasuf.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@EnableEurekaClient
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        log.info("POST /payment/create: {}", payment);
        int result = paymentService.create(payment);
        log.info("***Insert Payment Result：" + result);
        if (result > 0) {
            return new CommonResult(200, "Insert Successfully.", result);
        } else {
            return new CommonResult(444, "Insert Failed.", result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("GET /payment/get/{}", id);
        Payment payment = paymentService.getPaymentByID(id);
        log.info("***Get Payment Result：" + payment);
        if (payment != null) {
            return new CommonResult(200, "Query Successfully.", payment);
        } else {
            return new CommonResult(444, "Query Failed.", null);
        }
    }

}
