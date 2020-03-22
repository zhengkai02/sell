package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.req.OfferCatgReq;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferCatgResp;
import com.iwhalecloud.retail.offer.manager.OfferCatgManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @version 1.0
 * @ClassName OfferCatgController
 * @Author wangzhongbao
 * @Date 2019/3/20 13:38
 **/
@Api(tags = "商品目录")
@RestController
@RequestMapping("/offer")
@Slf4j
public class OfferCatgController {

    @Autowired
    OfferCatgManager offerCatgManager;

    /**
     * @Author wangzhongbao
     * @Description 供亚信调用，查询商品目录
     * @Date 15:16 2019/3/25
     * @Param [req]
     * @return com.iwhalecloud.retail.common.dto.ResultVO<java.util.ArrayList<com.iwhalecloud.retail.offer.dto.client.resp.OfferCatgResp>>
     **/
    @PostMapping(value = "/qryoffercatglist")
    public ResultVO<ArrayList<OfferCatgResp>> qryOfferCatg(@RequestBody OfferCatgReq req) {
        log.info("OfferCatgController qryOfferCatg start");
        ResultVO<ArrayList<OfferCatgResp>> result = offerCatgManager.qryOfferCatg(req);
        log.info("OfferCatgController qryOfferCatg end");
        return result;
    }

}
