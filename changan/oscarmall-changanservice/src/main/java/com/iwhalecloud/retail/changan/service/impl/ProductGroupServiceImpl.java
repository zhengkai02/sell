package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.changan.DTO.ProductGroupDTO;
import com.iwhalecloud.retail.changan.DTO.resp.AppResponseDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.dto.resp.TblStoreCatgResp;
import com.iwhalecloud.retail.changan.enums.GroupResultEnum;
import com.iwhalecloud.retail.changan.enums.SyncStateEnum;
import com.iwhalecloud.retail.changan.exception.ChangAnException;
import com.iwhalecloud.retail.changan.service.ProductGroupService;
import com.iwhalecloud.retail.changan.service.RequestHeaderService;
import com.iwhalecloud.retail.changan.service.TblStoreCatgService;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.changan.utils.ParamSign;
import com.iwhalecloud.retail.changan.utils.ResultVOUtils;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 同步店铺目录到长安的产品分类
 *
 * @author ZhengKai
 * @data 2019-11-07 14:17
 */
@Service
public class ProductGroupServiceImpl implements ProductGroupService {

    @Value("${changan.url}")
    private String path;

    @Autowired
    private RequestHeaderService requestHeaderService;

    @Autowired
    private TblStoreCatgService tblStoreCatgService;

    @Override
    public ResultVO<String> syncProductGroup(String groupId) {
        Optional<TblStoreCatgResp> tblStoreCatgRespOptional = Optional.ofNullable(tblStoreCatgService.qryStoreCatgDetail(groupId));
        if (!tblStoreCatgRespOptional.isPresent()) {
            throw new ChangAnException(GroupResultEnum.GROUP_NOT_EXIST);
        }
        TblStoreCatgResp tblStoreCatgResp = tblStoreCatgRespOptional.get();
        if (StringUtils.isEmpty(tblStoreCatgResp.getName())) {
            throw new ChangAnException(GroupResultEnum.GROUP_NAME_NOT_NULL);
        }
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        productGroupDTO.setGroupId(tblStoreCatgResp.getCatId());
        productGroupDTO.setGroupName(tblStoreCatgResp.getName());
        productGroupDTO.setPid(tblStoreCatgResp.getParentId());
        if (SyncStateEnum.ADD_NOT_SYNC.getCode() == tblStoreCatgResp.getSyncState()) {
            return createProductGroup(productGroupDTO);
        } else if (SyncStateEnum.UPDATE_NOT_SYNC.getCode() == tblStoreCatgResp.getSyncState()) {
            return updateProductGroup(productGroupDTO);
        }else {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getMessage(),"同步失败");
        }
    }

    @Override
    public ResultVO<String> createProductGroup(ProductGroupDTO productGroupDTO) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("groupId", productGroupDTO.getGroupId());
        requestBody.put("name", productGroupDTO.getGroupName());
        requestBody.put("code", productGroupDTO.getGroupCode());
        requestBody.put("pid", productGroupDTO.getPid());
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
        path += "/huservice/api/productGroup";
        String response = HttpClientUtil.sendPost(path, requestHeaders, requestBody);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "长安服务器未响应");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            tblStoreCatgService.updateStoreCatSyncState(productGroupDTO.getGroupId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    @Override
    public ResultVO<String> updateProductGroup(ProductGroupDTO productGroupDTO) {

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", productGroupDTO.getGroupName());
        requestParams.put("code", productGroupDTO.getGroupCode());
        requestParams.put("pid", productGroupDTO.getPid());

        Map<String, String> requestHeader = requestHeaderService.getHeader();
        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        Map<String, String> tempParams = new HashMap<>();
        tempParams.putAll(requestParams);
        tempParams.putAll(requestHeader);
        String sign = ParamSign.getSign(tempParams);
        tempParams = null;
        requestHeader.remove("appKey");
        requestHeader.put("sign", sign);
        // 发起远程http请求
        path += "/huservice/api/productGroup/" + productGroupDTO.getGroupId();
        String response = HttpClientUtil.sendPut(path, requestHeader, requestParams);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "长安服务器未响应");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            tblStoreCatgService.updateStoreCatSyncState(productGroupDTO.getGroupId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    @Override
    public ResultVO<String> deleteProductGroup(String productGroupId) {

        Map<String, String> requestHeaders = requestHeaderService.getHeader();
        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        String sign = ParamSign.getSign(requestHeaders);
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        // 发起远程http请求
        path += "/huservice/api/product/" + productGroupId;
        String response = HttpClientUtil.doDelete(path, requestHeaders);
        if (StringUtil.isBlank(response)) {
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
