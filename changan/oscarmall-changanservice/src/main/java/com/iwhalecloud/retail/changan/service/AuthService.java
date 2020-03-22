package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.DTO.resp.AuthTokenDTO;

/**
 * @author ZhengKai
 * @data 2019-10-31 13:25
 */
public interface AuthService {

    /**
     * 根据client_id获取长安接口访问令牌
     * @return
     */
    AuthTokenDTO getAccessToken(String appId, String appKey, String gantType, String scope);

    AuthTokenDTO refreshToken();

}
