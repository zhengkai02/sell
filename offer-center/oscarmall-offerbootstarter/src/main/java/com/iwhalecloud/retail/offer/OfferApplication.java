package com.iwhalecloud.retail.offer;

import com.github.tobato.fastdfs.FdfsClientConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by xh on 2019/2/13.
 */
@Import(FdfsClientConfig.class)
@SpringBootApplication(scanBasePackages = "com.iwhalecloud.retail")
@MapperScan("com.iwhalecloud.retail.*.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession
@ServletComponentScan("com.iwhalecloud.retail")
public class OfferApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfferApplication.class, args);
    }
}
