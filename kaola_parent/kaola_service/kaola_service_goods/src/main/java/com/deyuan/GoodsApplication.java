package com.deyuan;


import com.deyuan.utils.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages ={"com.deyuan.goods.dao"})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }
    @Value("${workerId}")
    private long workerId;
    @Value("${datacenterId}")
    private long datacenterId;

    @Bean
    public IdWorker createIdWork(){
        return new IdWorker(workerId,datacenterId);
    }
}
