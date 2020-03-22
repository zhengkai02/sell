package com.iwhalecloud.retail.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {
    private int expireSeconds;
    private String  host;
    private int port;
    private String password;
    private int connectionTimeout;
    private int soTimeout;
    private RedisProperties.Cluster cluster;
    private int maxIdle;
    private int maxTotal;
    private long maxWaitMilli;
    private int minIdle;
    private int numTestsPerEvictionRun;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    @Data
    public static class Cluster {
        private String nodes;
    }

}
