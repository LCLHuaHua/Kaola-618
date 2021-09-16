package com.deyuan;

import com.deyuan.utils.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @User LiChengLong
 * @Date 2021/8/2016:27
 * @Jia Jia开开心心
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.deyuan.order.fegin"})
public class OrderWebApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(OrderWebApplicaiton.class,args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
