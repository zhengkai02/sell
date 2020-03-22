package com.iwhalecloud.retail.changan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.iwhalecloud.retail")
@EnableFeignClients
public class ChanganApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChanganApplication.class, args);
    }

}
