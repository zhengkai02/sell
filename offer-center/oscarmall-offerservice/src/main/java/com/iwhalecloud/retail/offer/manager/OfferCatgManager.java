package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.req.OfferCatgReq;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferCatgResp;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @ClassName OfferCatgManager
 * @Author wangzhongbao
 * @Date 2019/3/20 13:49
 **/
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OfferCatgManager {

    @Autowired
    private ProdGoodsCatMapper prodGoodsCatMapper;

    public ResultVO<ArrayList<OfferCatgResp>> qryOfferCatg(OfferCatgReq req) {
        log.info("OfferCatgManager qryOfferCatg start, request = [{}]", req);

        ResultVO<ArrayList<OfferCatgResp>> resultVO = new ResultVO<>();

        if (req == null || null == req.getRequestId()) {
            resultVO.setCode(OfferBaseMessageCodeEnum.REQUEST_ID_IS_EXPIRED.getStatus());
            resultVO.setMessage(OfferBaseMessageCodeEnum.REQUEST_ID_IS_EXPIRED.getMessage());
            return resultVO;
        }
        try {
            List<OfferCatgResp> offerQryRespList = prodGoodsCatMapper.qryOfferCatg();
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            resultVO.setData((ArrayList<OfferCatgResp>) offerQryRespList);
        }
        catch (Exception e) {
            log.error("OfferCatgManager qryOfferCatg failed, Exception = [{}]", e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }

        log.info("OfferCatgManager qryOfferCatg end, response = [{}]", resultVO);
        return resultVO;

    }
}
