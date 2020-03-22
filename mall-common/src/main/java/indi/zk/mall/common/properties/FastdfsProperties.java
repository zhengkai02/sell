package com.iwhalecloud.retail.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by xh on 2019/7/10.
 */
@Data
@Component
@ConfigurationProperties(prefix = "fdfs")
public class FastdfsProperties {
    private String httpAntiStealToken;

    private String httpSecretKey;

    private String showUrl;
}
