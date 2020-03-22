package com.iwhalecloud.retail.changan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZhengKai
 * @data 2019-11-08 11:03
 */
@Data
@ConfigurationProperties(prefix = "changan")
@Component
public class ChangAnConfig {

    private String url;

    private String appId;

    private String appKey;

    private String grantType;

    private String scope;
}
