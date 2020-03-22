package com.iwhalecloud.retail.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ErrorCodeConfig {

    public ErrorCodeConfig() { }

    @Value("${errorCode.prefix}")
    public void setErrorCodePrefix(String errorCodePrefixInfo) {
        errorCodePrefix = errorCodePrefixInfo;
    }
    public static String errorCodePrefix;



}
