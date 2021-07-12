package com.nasuf.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-> PaymentFallbackService.paymentInfo_OK fall back.";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "-> PaymentFallbackService.paymentInfo_Timeout fall back.";
    }
}
