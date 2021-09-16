package com.deyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @User LiChengLong
 * @Date 2021/8/1819:12
 * @Jia Jia开开心心
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.deyuan.dao"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
