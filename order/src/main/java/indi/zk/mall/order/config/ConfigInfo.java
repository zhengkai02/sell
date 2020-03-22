package indi.zk.mall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZhengKai
 * @data 2019-10-17 00:18
 */
@Component
@ConfigurationProperties(prefix = "config")
@Data
public class ConfigInfo {
    private String info;

}
