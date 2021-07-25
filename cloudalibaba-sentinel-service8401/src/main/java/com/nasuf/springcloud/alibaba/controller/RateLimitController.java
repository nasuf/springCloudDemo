package com.nasuf.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nasuf.springcloud.alibaba.handler.CustomBlockHandler;
import com.nasuf.springcloud.entities.CommonResult;
import com.nasuf.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",
            blockHandlerClass = CustomBlockHandler.class,
            blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "Limit by resource name is successful.",
                new Payment(2020L, "serial2020"));
    }


}
