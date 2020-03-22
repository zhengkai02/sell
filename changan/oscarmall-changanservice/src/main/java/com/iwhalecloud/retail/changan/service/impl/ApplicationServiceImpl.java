package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.changan.DTO.req.AppRequestDTO;
import com.iwhalecloud.retail.changan.DTO.resp.AppResponseDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.dto.resp.AttrValue;
import com.iwhalecloud.retail.changan.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.changan.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.changan.enums.AppResultEnum;
import com.iwhalecloud.retail.changan.enums.SyncStateEnum;
import com.iwhalecloud.retail.changan.exception.ChangAnException;
import com.iwhalecloud.retail.changan.service.ApplicationService;
import com.iwhalecloud.retail.changan.service.GoodsCatService;
import com.iwhalecloud.retail.changan.service.RequestHeaderService;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.changan.utils.ParamSign;
import com.iwhalecloud.retail.changan.utils.ResultVOUtils;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ZhengKai
 * @data 2019-10-30 14:52
 */
@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    @Value("${changan.url}")
    private String path;

    @Autowired
    protected RequestHeaderService requestHeaderService;

    @Autowired
    private GoodsCatService goodsCatService;

    @Override
    public ResultVO syncApp(String appId) {

        Optional<QryGoodsCatListResp> respOptional = Optional.ofNullable(goodsCatService.qryGoodsCatDetail(appId));
        if (!respOptional.isPresent()){
            throw new ChangAnException(AppResultEnum.APP_NOT_EXIST);
        }
        QryGoodsCatListResp resp = respOptional.get();
        if (StringUtils.isEmpty(resp.getAppCode())){
            throw new ChangAnException(AppResultEnum.APP_CODE_NOT_NULL);
        }
        AppRequestDTO appRequestDTO = new AppRequestDTO();
        appRequestDTO.setAppId(resp.getCatId());
        appRequestDTO.setAppName(resp.getName());
        appRequestDTO.setAppCode(resp.getAppCode());

        List<GoodsCatAttrResp> goodsCatAttrRespList = resp.getNoSkuGoodsCatAttr();
        for (GoodsCatAttrResp goodsCatAttrResp : goodsCatAttrRespList) {
            if ("brandId" == goodsCatAttrResp.getAttrCode()) {
                List<AttrValue> attrValueList = goodsCatAttrResp.getAttrValueList();
                for (AttrValue attrValue : attrValueList) {
                    if ("brandId" == attrValue.getValue()) {
                        appRequestDTO.setBrandId(Integer.parseInt(attrValue.getValueMark()));
                    }
                }
            }
        }
        if (StringUtils.isEmpty(appRequestDTO.getBrandId())){
            throw new ChangAnException(AppResultEnum.APP_BRANDID_NOT_NULL);
        }
        if (SyncStateEnum.ADD_NOT_SYNC.getCode() == resp.getSyncState()) {
            return createApp(appRequestDTO);
        }else if (SyncStateEnum.UPDATE_NOT_SYNC.getCode() == resp.getSyncState()) {
            return updateApp(appRequestDTO);
        }else {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getMessage(),"同步失败");
        }

    }

    @Override
    public ResultVO<String> createApp(AppRequestDTO appRequestDTO) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("appid", appRequestDTO.getAppId());
        requestBody.put("brandId", String.valueOf(appRequestDTO.getBrandId()));
        requestBody.put("name", appRequestDTO.getAppName());
        requestBody.put("code", appRequestDTO.getAppCode());

        Map<String, String> requestHeaders = requestHeaderService.getHeader();
        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        Map<String, String> tempParams = new HashMap<>();
        tempParams.putAll(requestHeaders);
        tempParams.putAll(requestBody);
        String sign = ParamSign.getSign(tempParams);
        tempParams = null;
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);

        // 发起远程http请求
        path += "/huservice/api/service";
        String response = HttpClientUtil.sendPost(path, requestHeaders, requestBody);
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "长安服务器未响应");
        }
        if (result.getSuccess() == true) {
            goodsCatService.updateSaleCatSyncState(appRequestDTO.getAppId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    /**
     * 编辑应用
     *
     * @param appRequestDTO
     * @return
     */
    @Override
    public ResultVO<String> updateApp(AppRequestDTO appRequestDTO) {

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", appRequestDTO.getAppName());
        requestParams.put("code", appRequestDTO.getAppCode());
        Map<String, String> requestHeaders = requestHeaderService.getHeader();
        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        Map<String, String> tempParams = new HashMap<>();
        tempParams.putAll(requestParams);
        tempParams.putAll(requestHeaders);
        String sign = ParamSign.getSign(tempParams);
        tempParams = null;
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        // 发起远程http请求
        path += "/huservice/api/service/" + appRequestDTO.getAppId();
        String response = HttpClientUtil.sendPut(path, requestHeaders, requestParams);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            goodsCatService.updateSaleCatSyncState(appRequestDTO.getAppId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    /**
     * 根据appId删除应用
     *
     * @param appId
     * @return
     */
    @Override
    public ResultVO<String> deleteApp(String appId) {

        Map<String, String> requestHeaders = requestHeaderService.getHeader();

        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        String sign = ParamSign.getSign(requestHeaders);
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        // 发起远程http请求
        path += "/huservice/api/service/" + appId;
        String response = HttpClientUtil.doDelete(path, requestHeaders);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }
}
