package com.iwhalecloud.retail.changan.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-10-27 13:19
 */
public class ParamSign {
    public static String getSign(Map<String, String> paramMap) {

        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(appkey);
        for (String key : keyArray) {
            stringBuilder.append(key).append("=").append(paramMap.get(key)).append("&");
        }

//        stringBuilder.append(secret);
        String codes = stringBuilder.toString();

        // 字符串连接示例
        //

        // SHA-1编码， 这里使用的是Apache codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
        /*
         * 以下sha1签名代码效果等同
         * byte[] sha = org.apache.commons.codec.digest.DigestUtils.sha(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(codes));
         *  String sign = org.apache.commons.codec.binary.Hex.encodeHexString(sha).toUpperCase();
         */
        String sign = org.apache.commons.codec.digest.DigestUtils.sha256Hex(codes).toUpperCase();
        return sign;

        //签名示例
        //7D78381BC58E1DB1DBA4BD965916FE6B4D5DC892
    }
}
