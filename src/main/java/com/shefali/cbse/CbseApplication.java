package com.shefali.cbse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CbseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbseApplication.class, args);
    }

}
