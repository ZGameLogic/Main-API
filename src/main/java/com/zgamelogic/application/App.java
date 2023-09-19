package com.zgamelogic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.zgamelogic.controllers")
@EnableJpaRepositories("com.zgamelogic.data.database")
@EntityScan("com.zgamelogic.data.database")
@EnableScheduling
public class App {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/**").allowedOrigins("https://localhost");
            }
        };
    }
}
