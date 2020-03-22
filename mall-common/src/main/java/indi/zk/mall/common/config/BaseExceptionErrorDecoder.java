package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.exception.ErrorMessage;
import com.iwhalecloud.retail.common.utils.JsonUtil;
import com.rabbitmq.tools.json.JSONUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xh on 2019/3/15.
 */
@Configuration
@Slf4j
public class BaseExceptionErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.SC_INTERNAL_SERVER_ERROR && response.body() != null) {
            try {
                String body = Util.toString(response.body().asReader());
                ErrorMessage errorMessage = new ErrorMessage();
                JSONUtil.tryFill(errorMessage, (HashMap<String, Object>) JsonUtil.json2Object(body));
                return new BaseException(errorMessage);
            }
            catch (IOException e) {
                log.error("error for BaseExceptionErrorDecoder:" + e);
            }
        }
        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
