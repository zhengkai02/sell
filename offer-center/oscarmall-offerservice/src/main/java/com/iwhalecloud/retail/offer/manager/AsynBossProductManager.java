package com.iwhalecloud.retail.offer.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.req.QueryProductListReq;
import com.iwhalecloud.retail.offer.dto.client.resp.ProductList;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryProductListResp;
import com.iwhalecloud.retail.offer.dto.resp.ProductObj;
import com.iwhalecloud.retail.offer.dto.resp.QueryProductObjResp;
import com.iwhalecloud.retail.offer.service.ProductService;
import lombok.extern.slf4j.Slf4j;


/**
 * 产品
 * 调用亚信
 * @author fanxiaofei
 * @date 2019/03/13
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AsynBossProductManager {

    @Autowired
    private ProductService productService;

    public ResultVO<QueryProductObjResp> listProduct(QueryProductListReq req) throws BaseException {
        log.info("AsynBossProductManager listProduct start, QueryProductListReq = [{}]", req);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 校验入参
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getOrgId(), OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        AssertUtil.isNotNull(req.getPageNo(), OfferBaseMessageCodeEnum.PAGE_NO_IS_NULL);
        AssertUtil.isNotNull(req.getPageSize(), OfferBaseMessageCodeEnum.PAGE_SIZE_IS_NULL);
        if (StringUtils.isEmpty(req.getChannel())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);
        }
        req.setRequestId(UidGeneator.getUID());

        ResultVO<QueryProductListResp> resultVO = productService.listProduct(req);
        ResultVOCheckUtil.checkResultVO(resultVO);

        List<ProductObj> productObjList = new ArrayList<>();

        if (null != resultVO.getData() && CollectionUtils.isNotEmpty(resultVO.getData().getProductList())) {
            for (ProductList productList : resultVO.getData().getProductList()) {
                ProductObj productObj = new ProductObj();
                productObj.setCost(productList.getCost());
                productObj.setInventory(productList.getInventory());
                productObj.setProductCode(productList.getProductCode());
                productObj.setProductName(productList.getProductName());
                productObj.setUseWay(productList.getUseWay());
                productObj.setUseWayName(productList.getUseWayName());
                productObj.setIsCertification(productList.getIsCertification());

                try {

                    if (StringUtils.isNotEmpty(productList.getValidityStartDate())) {
                        productObj.setValidityStartDate(sdf.parse(productList.getValidityStartDate()));
                    }
                    if (StringUtils.isNotEmpty(productList.getValidityEndDate())) {
                        productObj.setValidityEndDate(sdf.parse(productList.getValidityEndDate()));
                    }

                }
                catch (ParseException e) {
                    log.error("Date formart error");
                    throw new BaseException(OfferBaseMessageCodeEnum.DATE_FORMART_ERROR);
                }
                productObjList.add(productObj);
            }
        }

        ResultVO<QueryProductObjResp> result = new  ResultVO<>();
        QueryProductObjResp queryProductObjResp = new QueryProductObjResp();
        queryProductObjResp.setPageNo(resultVO.getData().getPageNo());
        queryProductObjResp.setPageSize(resultVO.getData().getPageSize());
        queryProductObjResp.setTotalCount(resultVO.getData().getTotalCount());
        queryProductObjResp.setTotalPage(resultVO.getData().getTotalPage());
        queryProductObjResp.setProductList(productObjList);
        result.setData(queryProductObjResp);
        result.setCode(resultVO.getCode());
        result.setMessage(resultVO.getMessage());
        log.info("AsynBossProductManager listProduct end");
        return result;
    }
}
