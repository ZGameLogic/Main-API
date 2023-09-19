package com.zgamelogic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.zgamelogic.controllers")
@EnableJpaRepositories("com.zgamelogic.data.database")
@EntityScan("com.zgamelogic.data.database")
@EnableScheduling
public class App {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
