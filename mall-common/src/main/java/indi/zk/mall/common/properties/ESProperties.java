package com.iwhalecloud.retail.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by xh on 2019/5/14.
 */
@Data
@Component
@ConfigurationProperties(prefix = "es.cluster")
public class ESProperties {

    private String nodes;
}
