package com.nasuf.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("payment_routh", r ->
                        r.path("/guonei").uri("http://news.baidu.com/guonei"))
                .route("payment_routh", r ->
                        r.path("/mil") .uri("http://news.baidu.com/mil")
                ).build();
        return routes.build();
    }

}
