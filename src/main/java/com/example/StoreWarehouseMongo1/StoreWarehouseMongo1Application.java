package com.example.StoreWarehouseMongo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreWarehouseMongo1Application {

    public static void main(String[] args) {
        SpringApplication.run(StoreWarehouseMongo1Application.class, args);
    }

//    @Bean
//    public CommandLineRunner init(RoleRepository roleRepository) {
//
//        return args -> {
//
//            Role adminRole = roleRepository.findByRole("ADMIN");
//            if (adminRole == null) {
//                Role newAdminRole = new Role();
//                newAdminRole.setRole("ADMIN");
//                roleRepository.save(newAdminRole);
//            }
//        };
//
//    }

}
