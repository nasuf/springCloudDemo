package com.nasuf.springcloud.alibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nasuf.springcloud.entities.CommonResult;

public class CustomBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(444, "global handler");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(444, "global handler2");
    }

}
