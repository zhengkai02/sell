package com.iwhalecloud.retail.changan.service.impl;

import com.iwhalecloud.retail.changan.DTO.resp.AuthTokenDTO;
import com.iwhalecloud.retail.changan.config.ChangAnConfig;
import com.iwhalecloud.retail.changan.service.ApplicationService;
import com.iwhalecloud.retail.changan.service.AuthService;
import com.iwhalecloud.retail.changan.service.RequestHeaderService;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhengKai
 * @data 2019-11-07 16:56
 */
@Service
@Slf4j
public class RequestHeaderServiceImpl implements RequestHeaderService {

    @Autowired
    private ChangAnConfig changAnConfig;

    @Autowired
    private ICache redisCache;

    @Autowired
    private AuthService authService;



    public Map<String, String> getHeader() {

        Map<String, String> header = new HashMap<>();
        header.put("appKey", changAnConfig.getAppKey());
        // 每次请求长安接口时，从redis中获取最新的token，添加到请求头中
        AuthTokenDTO token =  (AuthTokenDTO) redisCache.get(CacheKeyDef.CHANGAN_RULE, CacheKeyDef.CHANGAN_ACCESS_TOLEN);
        if (StringUtils.isEmpty(token)){
            token = authService.refreshToken();
        }
        header.put("accessToken", token.getAccess_token());
        // 获取当前的时间戳
        long timestamp = System.currentTimeMillis();
        header.put("timestamp", String.valueOf(timestamp));
        // 生成10位随机字符串
        header.put("nonce", RandomStringUtils.randomAlphanumeric(10));
        return header;

    }
}
