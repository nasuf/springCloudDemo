package com.nasuf.springcloud.service;

import com.nasuf.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentByID(@Param("id") Long id);
}
