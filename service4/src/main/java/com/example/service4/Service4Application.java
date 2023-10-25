package com.example.service4;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo(scanBasePackages = "com.example.service4")
@PropertySource("classpath:dubbo.properties")
@SpringBootApplication
public class Service4Application {

    public static void main(String[] args) {
        SpringApplication.run(Service4Application.class, args);
    }

}
