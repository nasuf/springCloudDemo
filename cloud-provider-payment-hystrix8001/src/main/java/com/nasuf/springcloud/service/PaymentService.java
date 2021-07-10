package com.nasuf.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
}
