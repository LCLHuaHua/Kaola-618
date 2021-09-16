package com.deyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @User LiChengLong
 * @Date 2021/8/1723:21
 * @Jia Jia开开心心
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.deyuan.user.fegin")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }

    //把restTemplate注入进来
    @Bean(name="restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
