package com.iwhalecloud.retail.changan.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-10-30 17:47
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url            目的地址
     * @param requestHeaders 请求头，Map类型
     * @param requestBody    请求体，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> requestHeaders, Map<String, String> requestBody) {
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                postMethod.setRequestHeader(entry.getKey(), entry.getValue());
            }
            //参数设不能传NULL，可以传传空字符串
            NameValuePair[] data = new NameValuePair[requestBody.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }
            postMethod.setRequestBody(data);

            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            String result = postMethod.getResponseBodyAsString();

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 发送PUT请求
     *
     * @param url            目的地址
     * @param requestHeaders 请求头，Map类型
     * @param requestBody  请求体，Map类型。
     * @return 远程响应结果
     */
    public static String sendPut(String url, Map<String, String> requestHeaders, Map<String, String> requestBody) {
        try {
            PutMethod putMethod = new PutMethod(url);
            putMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                putMethod.setRequestHeader(entry.getKey(), entry.getValue());
            }
            //参数设不能传NULL，可以传传空字符串
            NameValuePair[] data = new NameValuePair[requestBody.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }
            putMethod.setQueryString(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(putMethod); // 执行PUT方法
            String result = putMethod.getResponseBodyAsString();

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 原生字符串发送put请求
     *
     * @param url
     * @param jsonStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPut(String url, String jsonStr, Map<String, String> requestHeaders) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPut.setHeader("DataEncoding", "UTF-8");
        if (!requestHeaders.isEmpty()) {
            for (Map.Entry entry : requestHeaders.entrySet()) {
                httpPut.setHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpPut.setEntity(new StringEntity(jsonStr));
            httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doDelete(String url, Map<String, String> requestHeaders) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpDelete.setConfig(requestConfig);
        httpDelete.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpDelete.setHeader("DataEncoding", "UTF-8");
        if (!requestHeaders.isEmpty()) {
            for (Map.Entry entry : requestHeaders.entrySet()) {
                httpDelete.setHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            // TODO
            e.printStackTrace();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 原生字符串发送post请求
     *
     * @param url
     * @param requestParams
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String requestParams, Map<String, String> requestHeaders) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("DataEncoding", "UTF-8");
        if (!requestHeaders.isEmpty()) {
            for (Map.Entry entry : requestHeaders.entrySet()) {
                httpPost.setHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            String requestBody = EntityUtils.toString(new StringEntity(requestParams));
            log.info("entity = [{}]", requestBody);
            httpPost.setEntity(new StringEntity(requestParams));

            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            // TODO
            e.printStackTrace();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String doPostForJson(String url, String requestParams, Map<String, String> requestHeaders) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("DataEncoding", "UTF-8");
        if (!requestHeaders.isEmpty()) {
            for (Map.Entry entry : requestHeaders.entrySet()) {
                httpPost.setHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            String requestBody = EntityUtils.toString(new StringEntity(requestParams));
            log.info("entity = [{}]", requestBody);
            httpPost.setEntity(new StringEntity(requestParams));

            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            // TODO
            e.printStackTrace();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}