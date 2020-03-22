package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.DTO.req.AppRequestDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.common.exception.BaseException;


/**
 * 应用接口
 * @author ZhengKai
 * @data 2019-11-06 11:36
 */
public interface ApplicationService {

    ResultVO<String> syncApp(String appId);

    /**
     * 创建应用接口
     * @param appRequestDTO
     * @return
     */
    ResultVO<String> createApp(AppRequestDTO appRequestDTO);

    /**
     * 编辑应用接口
     * @param appRequestDTO
     * @return
     */
    ResultVO<String> updateApp(AppRequestDTO appRequestDTO);

    /**
     * 删除应用接口
     * @param appId
     * @return
     */
    ResultVO<String> deleteApp(String appId);
}
