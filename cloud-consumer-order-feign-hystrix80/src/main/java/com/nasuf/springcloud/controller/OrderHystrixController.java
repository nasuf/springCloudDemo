package com.nasuf.springcloud.controller;

import com.nasuf.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallbackHandler")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
//            // consumer 客户端超时时间为1.5s，而PaymentService超时时间为5s，且实际运行时间为3s超过了1.5s，所以应调用paymentInfo_TimeoutHandler
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    /**
     * 此处开启服务降低，但是不指定处理的handler，则默认使用Controller配置的
     * @DefaultProperties(defaultFallback = "paymentGlobalFallbackHandler")
     */
    @HystrixCommand
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        int a = 10/0;   // exception出现，会调用paymentInfo_TimeoutHandler
        return paymentHystrixService.paymentInfo_Timeout(id);   // 或者请求服务端超时，会调用paymentInfo_TimeoutHandler
    }

    public String paymentInfo_TimeoutHandler(@PathVariable("id") Integer id) {
        return "Consumer Port 80, PaymentService is busy now. Please try again later.";
    }

    /**
     * global fallback
     */
    public String paymentGlobalFallbackHandler() {
        return "Global Fallback Exception Handler: Please try again later.";
    }
}
