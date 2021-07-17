package com.nasuf.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {
    /**
     * 正常访问，一定成功的方法
     */
    public String paymentInfo_OK(Integer id) {
        return "Thread Pool: " + Thread.currentThread().getName() + " paymentInfo_OK, id: " + id + "\t";
    }

    /**
     * 模拟访问耗时5秒，默认超时时间为3秒
     * 一旦访问失败，自动调用paymentInfo_TimeoutHandler处理
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_Timeout(Integer id) {
//        int a = 10/0;   // 假设运行出现异常，同样会执行paymentInfo_TimeoutHandler方法进行处理
        int time = 3000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread Pool: " + Thread.currentThread().getName() + " paymentInfo_Timeout, id: " + id + "\t 耗时(ms):" + time;
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "Thread Pool: " + Thread.currentThread().getName() + " network busy, please try again later, id: " + id;
    }

    /////////////服务熔断//////////////

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            // 在10s内，10次访问超过60%无法访问，断路器状态从CLOSE变为OPEN，即断路器开启
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),   // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   // 时间窗口期10s
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")    // 失败率达到60%后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*** id can not be negative");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "call successfully, serialNumber: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "*** id can not be negative. paymentCircuitBreaker_fallback called now. Please try again. id: " + id;
    }

}
