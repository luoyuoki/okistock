package com.oki.stock.cloud.stock.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.oki.stock.cloud.user.client")
@ComponentScan(basePackages = "com.oki.stock.cloud")
@EnableDiscoveryClient
@SpringBootApplication
//@SpringCloudApplication
public class CloudStockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStockServerApplication.class, args);
    }
}
