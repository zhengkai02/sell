package indi.zk.mall.product.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ZhengKai
 * @data 2019-10-27 22:14
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
