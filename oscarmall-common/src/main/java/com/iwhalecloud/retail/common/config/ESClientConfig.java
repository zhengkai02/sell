package com.iwhalecloud.retail.common.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.iwhalecloud.retail.common.properties.ESProperties;

/**
 * Created by xh on 2019/5/14.
 */
@Configuration
@ConditionalOnProperty(prefix = "es", value = "enabled", matchIfMissing = false, havingValue = "true")
public class ESClientConfig {

    private static final int PORT_NUM = 9200;
    private static final int HTTP_LENGTH = 2;

    @Autowired
    private ESProperties esProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] serverArray = StringUtils.commaDelimitedListToStringArray(esProperties.getNodes());
        HttpHost[] httpHostArray = new HttpHost[serverArray.length];
        for (int i = 0; i < serverArray.length; i++) {
            String[] httpHostStr = StringUtils.delimitedListToStringArray(serverArray[i], ":");
            int port = PORT_NUM;
            if (httpHostStr.length == HTTP_LENGTH) {
                port = Integer.parseInt(httpHostStr[1]);
            }
            HttpHost httpHost = new HttpHost(httpHostStr[0], port, "http");
            httpHostArray[i] = httpHost;
        }

        RestClientBuilder builder = RestClient.builder(httpHostArray);
        return new RestHighLevelClient(builder);
    }
}
