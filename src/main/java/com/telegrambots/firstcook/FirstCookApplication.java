package com.telegrambots.firstcook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FirstCookApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstCookApplication.class, args);
    }

}
