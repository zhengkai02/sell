package com.iwhalecloud.retail.changan.utils;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.SortedMap;

/**
 * @author ZhengKai
 * @data 2019-10-25 13:09
 */
public class SignUtil {

    /**
     * 将所有请求参数根据key进行排序加密，得到签名
     * @param params
     * @return
     */
    public static String getSign(Map<String, String> params) {
        // TODO 参数签名
        StringBuffer stringBuffer=new StringBuffer();
        for (Map.Entry entry : params.entrySet()) {
            if (!entry.getKey().equals("sign")) {
                if (!StringUtils.isEmpty(entry.getKey()) && !StringUtils.isEmpty(entry.getValue())) {
                    stringBuffer.append(entry.getKey()).append(entry.getKey());
                }
            }
        }
        return null;
    }

    public static boolean verifySign(SortedMap<String, String> params) {
        // TODO  验证签名结果
        return false;
    }
}
