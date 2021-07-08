package com.nasuf.lbrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RandomLBRule {
    @Bean
    public IRule rule() {
        return new RandomRule(); // 随机
    }
}
