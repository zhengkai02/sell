package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.changan.ChanganApplicationTests;
import com.iwhalecloud.retail.changan.DTO.resp.AuthTokenDTO;
import com.iwhalecloud.retail.changan.service.AuthService;
import com.iwhalecloud.retail.changan.utils.HtmlUtils;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author ZhengKai
 * @data 2019-11-11 14:25
 */
public class AuthServiceImplTest extends ChanganApplicationTests {

    @Value("${changan.url}")
    private String path;

    @Autowired
    private ICache redisCache;

    @Autowired
    private AuthService authService;

    @Test
    public void getAccessToken() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", "8B2F79AC4C7A4F2BB71A81046EB01E47");
        requestBody.put("client_secret", "L6fEBGI+fIWl4JvxbWAEOPw==");
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("scope", "cu");

        authService.refreshToken();
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("client_id","8B2F79AC4C7A4F2BB71A81046EB01E47");
//        requestParams.put("client_secret", "L6fEBGI+fIWl4JvxbWAEOPw==");
//        requestParams.put("grant_type", "client_credentials");
//        requestParams.put("scope", "cu");
//        Map<String, String> requestHeaders = new HashMap<>();
//        String params = JSON.toJSONString(requestParams);

//        path += "/oauth/token";
//        String response = HttpClientUtil.sendPost(path, requestHeaders, requestBody);
//        // 从返回的html内容提取<body>中的内容，如果返回的格式是json，可以直接提取json中的accessToken
////        String accessToken = HtmlUtils.html2Str(result).trim();
//        AuthTokenDTO token = JSON.parseObject(response, AuthTokenDTO.class);
//        // 将提取的信息存入redis，并设置key的有效期
//        redisCache.set(CacheKeyDef.CHANGAN_RULE, CacheKeyDef.CHANGAN_ACCESS_TOLEN, token);
        Object value =  redisCache.get(CacheKeyDef.CHANGAN_RULE, CacheKeyDef.CHANGAN_ACCESS_TOLEN);
        Assert.assertTrue(value != null);

    }
}