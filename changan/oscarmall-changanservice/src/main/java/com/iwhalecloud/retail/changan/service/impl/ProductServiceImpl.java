package com.iwhalecloud.retail.changan.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.changan.DTO.req.ProductRequestDTO;
import com.iwhalecloud.retail.changan.DTO.resp.AppResponseDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.changan.dto.resp.ProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.changan.enums.ProductEnum;
import com.iwhalecloud.retail.changan.enums.SyncStateEnum;
import com.iwhalecloud.retail.changan.exception.ChangAnException;
import com.iwhalecloud.retail.changan.service.ProdGoodsService;
import com.iwhalecloud.retail.changan.service.ProductService;
import com.iwhalecloud.retail.changan.service.RequestHeaderService;
import com.iwhalecloud.retail.changan.utils.HttpClientUtil;
import com.iwhalecloud.retail.changan.utils.ParamSign;
import com.iwhalecloud.retail.changan.utils.ResultVOUtils;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author ZhengKai
 * @data 2019-11-07 14:17
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Value("${changan.url}")
    private String path;

    @Autowired
    private RequestHeaderService requestHeaderService;

    @Autowired
    private ProdGoodsService prodGoodsService;

    @Override
    public ResultVO<String> syncProduct(String productId) {
        Optional<ProdGoodsDetailByIdResp> prodGoodsDetailByIdResp = Optional.ofNullable(prodGoodsService.queryProdGoodsDetailById(productId));
        if (!prodGoodsDetailByIdResp.isPresent()) {
            throw new ChangAnException(ProductEnum.PRODUCT_NOT_EXIST);
        }
        ProdGoodsDetailByIdResp prodGoods = prodGoodsDetailByIdResp.get();
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setAppId(prodGoods.getSaleCatId());
        productRequestDTO.setGroupId(prodGoods.getStoreCatId());
        productRequestDTO.setProductId(prodGoods.getGoodsId());
        productRequestDTO.setProductName(prodGoods.getName());
        productRequestDTO.setNumber(prodGoods.getStockQty());
        productRequestDTO.setEffectiveDate(String.valueOf(prodGoods.getMarketingBeginTime()));
        productRequestDTO.setExpiryDate(String.valueOf(prodGoods.getMarketingEndTime()));
        productRequestDTO.setPriceUnit("RMB");
        productRequestDTO.setPrice(String.valueOf(prodGoods.getPrice()));
        productRequestDTO.setUrl(prodGoods.getRealThumbnail());
        productRequestDTO.setUsageUnit("M");
        productRequestDTO.setUsageValue("100");
        productRequestDTO.setAlias(prodGoods.getSimpleName());
        productRequestDTO.setDescription(prodGoods.getIntro());
        if (SyncStateEnum.ADD_NOT_SYNC.getCode() == prodGoods.getSyncState()){
            return createProduct(productRequestDTO);
        }else if (SyncStateEnum.UPDATE_NOT_SYNC.getCode() == prodGoods.getSyncState()){
            return updateProduct(productRequestDTO);
        }else {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYNC_FAIL.getMessage(),"同步失败");
        }
    }

    @Override
    public ResultVO<String> createProduct(ProductRequestDTO productRequestDTO) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("productId", productRequestDTO.getProductId());
        requestBody.put("groupId", productRequestDTO.getGroupId());
        requestBody.put("brandId", String.valueOf(productRequestDTO.getBrandId()));
        requestBody.put("appid", productRequestDTO.getAppId());
        requestBody.put("name", productRequestDTO.getProductName());
        requestBody.put("alias", productRequestDTO.getAlias());
        requestBody.put("url", productRequestDTO.getUrl());
        requestBody.put("price", String.valueOf(productRequestDTO.getPrice()));
        requestBody.put("priceUnit", productRequestDTO.getPriceUnit());
        requestBody.put("number", String.valueOf(productRequestDTO.getNumber()));
        requestBody.put("effectiveDate", String.valueOf(productRequestDTO.getEffectiveDate()));
        requestBody.put("expiryDate", String.valueOf(productRequestDTO.getExpiryDate()));
        requestBody.put("description", productRequestDTO.getDescription());
        requestBody.put("usageUnit", productRequestDTO.getUsageUnit());
        requestBody.put("usageValue", productRequestDTO.getUsageValue());
        Map<String, String> requestHeaders = requestHeaderService.getHeader();

        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        Map<String, String> tempParams = new HashMap<>();
        tempParams.putAll(requestHeaders);
        tempParams.putAll(requestBody);
        String sign = ParamSign.getSign(tempParams);
        tempParams = null;
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        path += "/huservice/api/product";

        String response = HttpClientUtil.sendPost(path, requestHeaders, requestBody);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            prodGoodsService.updateGoodsSyncState(productRequestDTO.getProductId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    @Override
    public ResultVO<String> updateProduct(ProductRequestDTO productRequestDTO) {

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("groupId", productRequestDTO.getGroupId());
        requestParams.put("brandId", String.valueOf(productRequestDTO.getBrandId()));
        requestParams.put("appid", productRequestDTO.getAppId());
        requestParams.put("name", productRequestDTO.getProductName());
        requestParams.put("alias", productRequestDTO.getAlias());
        requestParams.put("url", productRequestDTO.getUrl());
        requestParams.put("price", String.valueOf(productRequestDTO.getPrice()));
        requestParams.put("priceUnit", productRequestDTO.getPriceUnit());
        requestParams.put("number", String.valueOf(productRequestDTO.getNumber()));
        requestParams.put("effectiveDate", String.valueOf(productRequestDTO.getEffectiveDate()));
        requestParams.put("expiryDate", String.valueOf(productRequestDTO.getExpiryDate()));
        requestParams.put("description", productRequestDTO.getDescription());
        requestParams.put("usageUnit", productRequestDTO.getUsageUnit());
        requestParams.put("usageValue", productRequestDTO.getUsageValue());

        Map<String, String> requestHeaders = requestHeaderService.getHeader();

        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        Map<String, String> tempParams = new HashMap<>();
        tempParams.putAll(requestParams);
        tempParams.putAll(requestHeaders);
        String sign = ParamSign.getSign(tempParams);
        tempParams = null;
        // appKey不能作为请求参数提交
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        path += "/huservice/api/product/" + productRequestDTO.getProductId();

        String response = HttpClientUtil.sendPut(path, requestHeaders, requestParams);
        if (StringUtils.isEmpty(response)) {
            return ResultVOUtils.fail(CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getStatus(),
                    CommonBaseMessageCodeEnum.CHANGAN_SYSTEM_ERROR.getMessage(), "长安服务器");
        }
        AppResponseDTO result = JSON.parseObject(response, AppResponseDTO.class);
        if (result.getSuccess() == true) {
            prodGoodsService.updateGoodsSyncState(productRequestDTO.getProductId(), SyncStateEnum.SYNCED.getCode());
            return ResultVOUtils.success(result.getSuccess());
        }
        return ResultVOUtils.fail(String.valueOf(result.getCode()), result.getMsg(), result.getSuccess());
    }

    @Override
    public ResultVO<String> deleteProduct(String productId) {

        Map<String, String> requestHeaders = requestHeaderService.getHeader();

        // 签名规则，将请求参数加上appKey、accessToken、timestamp(时间戳)、nonce，再根据ISCII码值由小到大排序，生成签名。
        String sign = ParamSign.getSign(requestHeaders);
        requestHeaders.remove("appKey");
        requestHeaders.put("sign", sign);
        path += "/huservice/api/product/" + productId;

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
