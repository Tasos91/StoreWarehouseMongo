package com.example.StoreWarehouseMongo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class StoreWarehouseMongo1Application {

    public static void main(String[] args) {
        SpringApplication.run(StoreWarehouseMongo1Application.class, args);
    }

}
