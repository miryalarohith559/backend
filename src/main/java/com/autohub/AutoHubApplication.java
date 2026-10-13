package com.autohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoHubApplication.class, args);
        System.out.println("=================================================");
        System.out.println(" AUTO HUB Backend is running on port 8081!");
        System.out.println(" Swagger/API Base: http://localhost:8081/api");
        System.out.println("=================================================");
    }
}
