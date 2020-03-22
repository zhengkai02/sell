package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.client.req.OfferDetailMobileQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferMobileQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferSearchReq;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferMobileQryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferQryResp;
import com.iwhalecloud.retail.offer.dto.req.CpspQueryOfferDetailsReq;
import com.iwhalecloud.retail.offer.dto.req.CpspQueryOfferListReq;
import com.iwhalecloud.retail.offer.dto.req.OscarMallAuditResultReq;
import com.iwhalecloud.retail.offer.dto.resp.CpspQueryOfferDetailsResp;
import com.iwhalecloud.retail.offer.dto.resp.CpspQueryOfferListResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.manager.OfferManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @version 1.0
 * @ClassName OfferController
 * @Author wangzhongbao
 * @Date 2019/3/20 12:14
 **/
@Api(tags = "商品")
@RestController
@RequestMapping("/offer")
@Slf4j
public class OfferController {


    @Autowired
    OfferManager offerManager;

    /**
     * @Author wangzhongbao
     * @Description 提供给亚信调用接口
     * @Date 15:14 2019/3/25
     * @Param [req]
     * @return com.iwhalecloud.retail.common.dto.ResultVO<java.util.ArrayList<com.iwhalecloud.retail.offer.dto.client.resp.OfferQryResp>>
     **/
    @PostMapping(value = "/qryofferlist")
    public ResultVO<ArrayList<OfferQryResp>> qryOfferList(@RequestBody OfferQryReq req) {
        log.info("OfferController qryOfferList start");
        ResultVO<ArrayList<OfferQryResp>> result = offerManager.qryOfferList(req);
        log.info("OfferController qryOfferList end");
        return result;
    }

    /**
     * @Author wangzhongbao
     * @Description 供H5调用查询offer接口
     * @Date 15:06 2019/3/25
     * @Param [req]
     * @return java.util.List<com.iwhalecloud.retail.offer.dto.client.resp.OfferMobileQryResp>
     **/
    @PostMapping(value = "/mobile/offerlist")
    public ResultVO<ArrayList<OfferMobileQryResp>> qryOfferListForMobile(@RequestBody OfferMobileQryReq req) {
        return ResultVOCheckUtil.buildResultVO(offerManager::qryOfferListForMobile, req);
    }


    @ApiOperation(value = "CPSP商品列表查询")
    @PostMapping(value = "/cpsp/offerlist")
    public ResultVO<CpspQueryOfferListResp> qryCpspOfferList(@RequestBody CpspQueryOfferListReq req) {
        log.info("OfferController qryCpspOfferList start");
        ResultVO<CpspQueryOfferListResp> result = ResultVOCheckUtil.buildResultVO(offerManager::qryCpspOfferList, req);
        log.info("OfferController qryCpspOfferList start");
        return result;
    }


    @PostMapping(value = "/offerlist/search")
    public ResultVO<ArrayList<OfferMobileQryResp>> searchOfferList(@RequestBody OfferSearchReq req) {
        return ResultVOCheckUtil.buildResultVO(offerManager::searchOfferList, req);
    }


    /**
     * @Author wangzhongbao
     * @Description 供H5调用查询商品详情接口
     * @Date 15:07 2019/3/25
     * @Param [req]
     * @return com.iwhalecloud.retail.offer.dto.resp.ProdGoodsDetailByIdResp
     **/
    @PostMapping(value = "/mobile/offerdetail")
    @ResponseStatus(value = HttpStatus.OK)
    public ResultVO<ProdGoodsDetailByIdResp> qryOfferDetail(@RequestBody OfferDetailMobileQryReq req) {
        log.info("OfferController qryOfferDetail start");
        ResultVO<ProdGoodsDetailByIdResp> result = ResultVOCheckUtil.buildResultVO(offerManager::qryOfferDetail, req);
        log.info("OfferController qryOfferDetail end");
        return result;
    }


    @ApiOperation(value = "CPSP查询商品详情")
    @PostMapping(value = "/cpsp/offerdetail")
    public ResultVO<CpspQueryOfferDetailsResp> qryCpspOfferDetail(@RequestBody CpspQueryOfferDetailsReq req) {
        log.info("OfferController qryCpspOfferDetail start");
        ResultVO<CpspQueryOfferDetailsResp> result = ResultVOCheckUtil.buildResultVO(offerManager::qryCpspOfferDetail, req);
        log.info("OfferController qryCpspOfferDetail end");
        return result;
    }


    /**
     * MQ通知审批商品结果
     * @param req OscarMallAuditResultReq
     * @return ResultVO
     */
    @PostMapping("/audit")
    public ResultVO auditOffer(@RequestBody OscarMallAuditResultReq req) {
        log.info("OfferController auditOffer start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(offerManager::auditOffer, req);
        log.info("OfferController auditOffer end");
        return result;
    }

}
