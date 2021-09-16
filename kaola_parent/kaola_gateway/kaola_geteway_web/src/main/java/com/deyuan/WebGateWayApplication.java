package com.deyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @User LiChengLong
 * @Date 2021/8/1911:29
 * @Jia Jia开开心心
 */
@SpringBootApplication
@EnableEurekaClient
public class WebGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebGateWayApplication.class,args);
    }

}
