package com.iwhalecloud.retail.changan.utils;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 从url中获取访问令牌access_token
 *
 * @author ZhengKai
 * @data 2019-10-31 14:47
 */
public class TokenUtil {

    // 授予方式，长安接口设定的是固定值
    private static final String GRANT_TYPE = "client_credentials";

    public static Map<String, String> getToken(String baseUrl, String appId, String appKey, String scope) {

        String url = baseUrl + "?client_id=" + appId + "&client_secret=" + appKey + "&grant_type=" + GRANT_TYPE + "&scope=" + scope;
        Map<String, String> map = new HashMap<>();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            // 定义BufferedReader读取UR中的响应
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connection.disconnect();
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = new JSONObject((Object) result);
            map.put("access_token", jsonObject.getString("access_token"));
            map.put("expires_in", jsonObject.getString("expires_in"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return map;
    }
}
