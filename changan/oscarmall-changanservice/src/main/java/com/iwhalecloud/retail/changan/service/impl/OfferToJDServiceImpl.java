package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.changan.DTO.MultiRecordsDTO;
import com.iwhalecloud.retail.changan.DTO.OfferToJDRequestDTO;
import com.iwhalecloud.retail.changan.DTO.OfferToJDResponseDTO;
import com.iwhalecloud.retail.changan.VO.OfferToJDResultVO;
import com.iwhalecloud.retail.changan.service.OfferToJDService;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.changan.utils.ResultVOUtils;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OfferToJDServiceImpl implements OfferToJDService {

    private String path;
    /**
     * 通用通骏接口2.7
     * @author wanghw
     * @data 2019-11-18
     */
    public OfferToJDResultVO commonNotification(MultiRecordsDTO multiDTO){
        HashMap<String, Object> map = new HashMap();
        map.put("enterpriseId", multiDTO.getEnterpriseId());
        map.put("multiRecords", multiDTO.getMultiRecords());
        String str = JSONObject.toJSONString(map);
        path = "https://cdpuat.faw-vw.com/mno-service/public/v1/CI_CommonNotification?X-NameSpace-Code=cdp-uat&X-MicroService-Name=mno-service";
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("X-NameSpace-Code", "cdp-uat");
        requestHeaders.put("X-MicroService-Name", "mno-service");//api-gateway
        String response = HttpClientUtil.doPostForJson(path, str , requestHeaders);
        if (StringUtils.isEmpty(response)) {
            String[] message = {"捷达系统未响应,通用通骏接口调用失败！"};
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(), message);
        }
        OfferToJDResponseDTO result = JSON.parseObject(response, OfferToJDResponseDTO.class);
        if("0000".equals(result.getStatus())){
            return ResultVOUtils.success(CommonBaseMessageCodeEnum.SUCCESS.getMessage(), result.getMessages(), result.getResult());
        }
        return ResultVOUtils.success(result.getStatus(), result.getMessages());
    }

    /**
     * 商品同步到捷达接口2.8
     * @author wanghw
     * @data 2019-11-17
     */
    public OfferToJDResultVO syncOfferToJD(OfferToJDRequestDTO offerToJDRequestDTO){
        HashMap<String, Object> productListMap = new HashMap();
        productListMap.put("enterpriseId", offerToJDRequestDTO.getEnterpriseId());
        productListMap.put("productSerial", offerToJDRequestDTO.getProductSerial());
        productListMap.put("productName", offerToJDRequestDTO.getProductName());
        productListMap.put("productDescription", offerToJDRequestDTO.getProductDescription());
        productListMap.put("price", offerToJDRequestDTO.getPrice());
        productListMap.put("status", offerToJDRequestDTO.getStatus());
        productListMap.put("activeTime", offerToJDRequestDTO.getActiveTime());
        productListMap.put("expireTime", offerToJDRequestDTO.getExpireTime());
        productListMap.put("purchaseMode", offerToJDRequestDTO.getPurchaseMode());
        productListMap.put("eSIMTypes", offerToJDRequestDTO.getESIMTypes());
        if(null != offerToJDRequestDTO.getThreshold()){
            productListMap.put("threshold", offerToJDRequestDTO.getThreshold());
        }
        if(null != offerToJDRequestDTO.getPeriod()){
            productListMap.put("period", offerToJDRequestDTO.getPeriod());
        }
        productListMap.put("attributes", offerToJDRequestDTO.getAttributes());
        productListMap.put("packageList", offerToJDRequestDTO.getPackageList());
        productListMap.put("syncType", offerToJDRequestDTO.getSyncType());
        productListMap.put("invoiceContent", offerToJDRequestDTO.getInvoiceContent());

        List attributesList = offerToJDRequestDTO.getAttributes();
        List packageList = offerToJDRequestDTO.getPackageList();
        productListMap.put("attributes",attributesList);
        productListMap.put("packageList",packageList);
        List li = new ArrayList();
        li.add(productListMap);
        String str = JSON.toJSONString(li);
        StringBuffer stringBuffer = new StringBuffer("{\"productList\":");
        stringBuffer.append(str).append("}");
        path = "https://cdpuat.faw-vw.com/mno-service/public/v1/EBO/CI_ProductsSync?X-NameSpace-Code=cdp-uat&X-MicroService-Name=mno-service";
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("X-NameSpace-Code", "cdp-uat");
        requestHeaders.put("X-MicroService-Name", "mno-service");
        //String obj  = JSON.toJSONString(offerToJDRequestDTO);
        String response = HttpClientUtil.doPostForJson(path, stringBuffer.toString() ,requestHeaders);
        if (StringUtils.isEmpty(response)) {
            String[] message = {"捷达系统未响应,商品同步接口调用失败！"};
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(), message);
        }
        OfferToJDResponseDTO result = JSON.parseObject(response, OfferToJDResponseDTO.class);
        if(result.getResult()!= null && !result.getResult().isEmpty()){
            return ResultVOUtils.success(result.getStatus(), result.getMessages(), result.getResult());
        }
        return ResultVOUtils.success(result.getStatus(), result.getMessages());
    }

}
