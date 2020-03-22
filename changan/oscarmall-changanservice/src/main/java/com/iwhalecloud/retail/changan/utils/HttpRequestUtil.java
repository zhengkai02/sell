package com.iwhalecloud.retail.changan.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-10-30 15:44
 */
@Slf4j
public class HttpRequestUtil {
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr, Map<String, String> requestHeader) {
        JSONObject jsonObject = null;
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (!requestHeader.isEmpty()) {
                for (Map.Entry entry : requestHeader.entrySet()) {
                    connection.setRequestProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
            if ("GET".equalsIgnoreCase(requestMethod)) {
                connection.connect();
            }
            if (null != outputStr) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            bufferedReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            jsonObject = JSONObject.fromObject(stringBuffer.toString());
        } catch (ConnectException connectException) {
            connectException.printStackTrace();
            log.info("Changan server connection timed out");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Http request error={[]}", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
