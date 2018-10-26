package com.oki.stock.cloud.order.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.oki.stock.cloud.user.client")
@ComponentScan(basePackages = "com.oki.stock.cloud")
@EnableDiscoveryClient
@SpringBootApplication
public class CloudOrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudOrderServerApplication.class, args);
    }
}
