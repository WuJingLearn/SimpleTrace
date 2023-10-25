package com.example.service1;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo(scanBasePackages = "com.example.service1")
@PropertySource("classpath:dubbo.properties")
@SpringBootApplication
public class Service1Application {
//-javaagent:/Users/jingmac/IdeaProjects/simpleTrace/agent/target/agent-1.0-SNAPSHOT.jar
    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

}
