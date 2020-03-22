package com.iwhalecloud.retail.changan.controller;

import com.iwhalecloud.retail.changan.DTO.resp.AuthTokenDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.AuthService;
import com.iwhalecloud.retail.changan.utils.ResultVOUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengKai
 * @data 2019-10-28 14:49
 */
@RestController
@RequestMapping(value = "/oauth", produces = "application/json")
@Data
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 远程调用长安获取token令牌存入redis
     *
     * @return
     */
    @PostMapping(value = "/token")
    public ResultVO<String> getAccessToken(@RequestParam("client_id") String appId,
                                   @RequestParam("client_secret") String appKey,
                                   @RequestParam("grant_type") String grantType,
                                   @RequestParam("scope") String scope) {
        // TODO 获取访问令牌
        log.info("AuthController getAccessToken start");
         AuthTokenDTO token = authService.getAccessToken(appId, appKey, grantType, scope);
        return ResultVOUtils.success(token);
    }
}
