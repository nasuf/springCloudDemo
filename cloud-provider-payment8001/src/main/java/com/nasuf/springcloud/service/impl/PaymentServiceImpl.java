package com.nasuf.springcloud.service.impl;

import com.nasuf.springcloud.dao.PaymentDao;
import com.nasuf.springcloud.entities.Payment;
import com.nasuf.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    public Payment getPaymentByID(Long id) {
        return paymentDao.getPaymentByID(id);
    }
}
