package com.deyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @User LiChengLong
 * @Date 2021/8/2011:41
 * @Jia Jia开开心心
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.deyuan.goods.fegin"})
@MapperScan(basePackages = {"com.deyuan.dao"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
