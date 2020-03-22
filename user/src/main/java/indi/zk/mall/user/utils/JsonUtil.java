package indi.zk.mall.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author ZhengKai
 * @data 2019-11-17 22:37
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     * @param jsonString
     * @param classType
     * @return
     */
    public static Object fromJson(String jsonString, Class classType) {
        try {
            return objectMapper.readValue(jsonString, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     * @param jsonString
     * @param typeReference
     * @return
     */
    public static Object fromJson(String jsonString, TypeReference typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
