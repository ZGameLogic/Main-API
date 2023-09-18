package com.zgamelogic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.zgamelogic.controllers"})
@EnableScheduling
public class App {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
