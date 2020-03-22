package com.iwhalecloud.retail.changan.controller;

import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ZhengKai
 * @data 2019-10-28 14:22
 */
@RestController
@RequestMapping("/huservice/api")
@Slf4j
public class AppController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 根据应用ID(销售目录的cat_id)同步应用给长安.
     * @param appId
     * @return
     */
    @PostMapping("/syncApp/{appId}")
    public ResultVO<String> syncSaleCatag(@NotEmpty(message = "catId不能为空") @PathVariable String appId) {
        log.info("Appcontroller synsApp start, appId = [{}]", appId);
        ResultVO<String> result = applicationService.syncApp(appId);
        log.info("Application syncApp start, result = [{}]", result);
        return result;
    }

    /**
     * 应用删除接口
     * @param appId
     * @return
     */
    @PostMapping("/app/delete")
    public ResultVO<String> deleteApp(String appId) {
        log.info("ProductGroupController deleteProductGroup start, productGroupId = [{}]", appId);
        ResultVO<String> result = applicationService.deleteApp(appId);
        log.info("ProductGroupController deleteProductGroup end, result = [{}]", result);
        return result;
    }

}
