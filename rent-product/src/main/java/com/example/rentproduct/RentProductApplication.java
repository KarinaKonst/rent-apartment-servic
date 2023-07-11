package com.example.rentproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RentProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentProductApplication.class, args);
    }

}
