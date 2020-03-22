package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.changan.DTO.resp.AuthTokenDTO;
import com.iwhalecloud.retail.changan.config.ChangAnConfig;
import com.iwhalecloud.retail.changan.service.AuthService;
import com.iwhalecloud.retail.changan.service.RequestHeaderService;
import com.iwhalecloud.retail.changan.utils.HtmlUtils;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取长安的token令牌并存入redis
 *
 * @author ZhengKai
 * @data 2019-11-06 11:45
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${changan.url}")
    private String path;

//    @Value("${changan.appId}")
//    private String appId;
//
//    @Value("${changan.appKey}")
//    private String appKey;
//
//    @Value("${changan.grantType}")
//    private String grantType;
//
//    @Value("${changan.scope}")
//    private String scope;
    @Autowired
    private ChangAnConfig changAnConfig;

    @Autowired
    private ICache redisCache;

    @Override
    public AuthTokenDTO getAccessToken(String appId, String appKey, String grantType, String scope) {

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("client_id", appId);
        requestParams.put("client_secret", appKey);
        requestParams.put("grant_type", grantType);
        requestParams.put("scope", scope);
        Map<String, String> requestHeaders = new HashMap<>();
        path += "/oauth/token";
        String response = HttpClientUtil.sendPost(path, requestParams, requestHeaders);
        // 从返回的html内容提取<body>中的内容，如果返回的格式是json，可以直接提取json中的accessToken
//        String accessToken = HtmlUtils.html2Str(result).trim();
        AuthTokenDTO token = JSON.parseObject(response, AuthTokenDTO.class);
        redisCache.set(CacheKeyDef.CHANGAN_RULE, CacheKeyDef.CHANGAN_ACCESS_TOLEN, token);
        return token;
    }

    @Override
    public AuthTokenDTO refreshToken() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("client_id", changAnConfig.getAppId());
        requestParams.put("client_secret", changAnConfig.getAppKey());
        requestParams.put("grant_type", changAnConfig.getGrantType());
        requestParams.put("scope", changAnConfig.getScope());
        Map<String, String> requestHeaders = new HashMap<>();
        path += "/oauth/token";
        String response = HttpClientUtil.sendPost(path, requestHeaders, requestParams);
        // 从返回的html内容提取<body>中的内容，如果返回的格式是json，可以直接提取json中的accessToken
//        String accessToken = HtmlUtils.html2Str(result).trim();
        AuthTokenDTO token = JSON.parseObject(response, AuthTokenDTO.class);
        redisCache.set(CacheKeyDef.CHANGAN_RULE, CacheKeyDef.CHANGAN_ACCESS_TOLEN, token);
        return token;
    }
}
