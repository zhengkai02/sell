package com.iwhalecloud.retail.changan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = "com.iwhalecloud.retail")
@EnableDiscoveryClient
@EnableRedisHttpSession
public class ChanganApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChanganApplication.class, args);
    }

}
