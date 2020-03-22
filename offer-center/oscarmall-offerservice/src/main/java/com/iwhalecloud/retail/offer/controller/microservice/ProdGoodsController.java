package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.offer.dto.req.AddProdBuyCountReq;
import com.iwhalecloud.retail.offer.dto.req.AddProdGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.req.ModGoodsTextInfoReq;
import com.iwhalecloud.retail.offer.dto.req.ModProdGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryTargetReq;
import com.iwhalecloud.retail.offer.dto.req.PublishGoodReq;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsListReq;
import com.iwhalecloud.retail.offer.dto.req.SynMgtInvoiceCatgNameReq;
import com.iwhalecloud.retail.offer.dto.resp.AddProdGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.ManagementProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsDetailByIdResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsInfoResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsRelResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsContentListResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsTextInfoResp;
import com.iwhalecloud.retail.offer.dto.resp.SynMgtInvoiceCatgNameResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.manager.ProdGoodsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 *
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Api(tags = "商品")
@Slf4j
@RestController("prodGoodsMicroService")
@CrossOrigin
@RequestMapping("/offer")
public class ProdGoodsController {

    @Autowired
    private ProdGoodsManager manager;

    @ApiOperation(value = "根据商品id查询商品详情")
    @GetMapping(value = "/prodgoods/{goodsId}")
    public ManagementProdGoodsDetailByIdResp queryProdGoodsDetailById(
        @ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsController queryProdGoodsDetailById start");
        ManagementProdGoodsDetailByIdResp result = manager.queryProdGoodsDetailById(goodsId);
        log.info("ProdGoodsController queryProdGoodsDetailById end");
        return result;
    }

    @ApiOperation(value = "根据goodsId列表，批量查询商品列表，用于分库查询")
    @PostMapping(value = "/prodgoods/list")
    public ResultVO<ArrayList<ProdGoods>> queryProdGoodsListByIds(@RequestBody QryProdGoodsListReq qryProdGoodsListReq)
        throws BaseException {
        log.info("ProdGoodsController queryProdGoodsListByIds start qryProdGoodsListReq = [{}]", qryProdGoodsListReq);
        ResultVO<ArrayList<ProdGoods>> result = ResultVOCheckUtil.buildResultVO(manager::queryProdGoodsListByIds,
            qryProdGoodsListReq);
        log.info("ProdGoodsController queryProdGoodsListByIds end");
        return result;
    }

    @ApiOperation(value = "根据商品id和内容类型查询商品关联的内容详情")
    @PostMapping(value = "/prodgoods/content/list")
    public List<QryGoodsContentListResp> qryProdGoodsContentByIdAndType(
        @RequestBody ProdGoodsQueryContentReq queryContentReq) throws BaseException {
        log.info("ProdGoodsController qryProdGoodsContentByIdAndType start queryContentReq = [{}]", queryContentReq);
        List<QryGoodsContentListResp> resp = manager.qryProdGoodsContentByIdAndType(queryContentReq);
        log.info("ProdGoodsController qryProdGoodsContentByIdAndType end");
        return resp;
    }

    @PostMapping("/prodgoods/add")
    public AddProdGoodsResp addProdGoods(@RequestBody AddProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsController addProdGoods start");
        AddProdGoodsResp result = manager.addProdGoods(request);
        log.info("ProdGoodsController addProdGoods end");
        return result;
    }

    @PostMapping("/prodgoods/mod/{goodsId}")
    public void modProdGoods(@ApiParam(value = "商品ID") @PathVariable String goodsId,
        @RequestBody ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsController modProdGoods start");
        manager.modProdGoods(goodsId, request);
        log.info("ProdGoodsController modProdGoods end");
    }

    @PostMapping("/prodgoods/operationinfo/mod/{goodsId}")
    public void modProdGoodsOperationInfo(@ApiParam(value = "商品ID") @PathVariable String goodsId,
        @RequestBody ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsController modProdGoodsOperationInfo start");
        manager.modProdGoodsOperationInfo(goodsId, request);
        log.info("ProdGoodsController modProdGoodsOperationInfo end");
    }

    @PostMapping("/prodgoods/stockinfo/mod/{goodsId}")
    public void modProdGoodsStockInfo(@ApiParam(value = "商品ID") @PathVariable String goodsId,
        @RequestBody ModProdGoodsReq request) throws BaseException {
        log.info("ProdGoodsController modProdGoodsStockInfo start");
        manager.modProdGoodsStockInfo(goodsId, request);
        log.info("ProdGoodsController modProdGoodsStockInfo end");
    }

    @PostMapping("/prodgoods/del/{goodsId}")
    public void delProdGoods(@ApiParam(value = "商品ID") @PathVariable String goodsId,
        @ApiParam(value = "用户ID") @Param("userId") Long userId) throws BaseException {
        log.info("ProdGoodsController delProdGoods start");
        manager.delProdGoods(goodsId, userId);
        log.info("ProdGoodsController delProdGoods end");
    }

    @PostMapping("/prodgoods/batchdel/{goodsIds}")
    public void batchDelProdGoods(@ApiParam(value = "商品ID") @PathVariable String goodsIds,
        @ApiParam(value = "用户ID") @Param("userId") Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsController batchDelProdGoods start");
        manager.batchDelProdGoods(goodsIds, userId, httpServletRequest);
        log.info("ProdGoodsController batchDelProdGoods end");
    }

    @PostMapping("/prodgoods/batchputon/{goodsIds}")
    public void batchPutOnProdGoods(@ApiParam(value = "商品ID") @PathVariable String goodsIds,
        @ApiParam(value = "用户ID") @Param("userId") Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsController batchPutOnProdGoods start");
        manager.batchPutOnProdGoods(goodsIds, userId, httpServletRequest);
        log.info("ProdGoodsController batchPutOnProdGoods end");
    }

    @PostMapping("/prodgoods/batchpulloff/{goodsIds}")
    public void batchPullOffProdGoods(@ApiParam(value = "商品ID") @PathVariable String goodsIds,
        @ApiParam(value = "用户ID") @Param("userId") Long userId, HttpServletRequest httpServletRequest)
        throws BaseException {
        log.info("ProdGoodsController batchPullOffProdGoods start");
        manager.batchPullOffProdGoods(goodsIds, userId, httpServletRequest);
        log.info("ProdGoodsController batchPullOffProdGoods end");
    }

    @PostMapping("/auditrequest/{goodsId}/{state}")
    public int goodsAuditRequest(@ApiParam(value = "商品ID") @PathVariable String goodsId,
        @ApiParam(value = "状态") @PathVariable String state) throws BaseException {
        log.info("ProdGoodsController goodsAuditRequest start");
        int result = manager.goodsAuditRequest(goodsId, state);
        log.info("ProdGoodsController goodsAuditRequest end");
        return result;
    }

    @PostMapping("/unpublish/{goodsId}")
    public int goodsUnpublish(@ApiParam(value = "商品ID") @PathVariable String goodsId, @RequestBody PublishGoodReq req)
        throws BaseException {
        log.info("ProdGoodsController goodsUnpublish start");
        int result = manager.goodsUnpublish(goodsId, req);
        log.info("ProdGoodsController goodsUnpublish end");
        return result;
    }

    /**
     * 商品列表查询
     */
    @PostMapping(value = "/queryOffer")
    public Page<ProdGoodsDTO> queryGoodsForPage(@RequestBody ProdGoodsQueryReq req) throws BaseException {
        log.info("ProdGoodsController queryGoodsForPage start");
        Page<ProdGoodsDTO> prodGoodsDTOPage = manager.queryGoodsForPage(req);
        List<ProdGoodsDTO> prodGoodsDTOList = prodGoodsDTOPage.getRecords();
        if (CollectionUtils.isEmpty(prodGoodsDTOList)) {
            Page<ProdGoodsDTO> page = new Page<>();
            page.setRecords(Lists.newArrayList());
            return page;
        }
        log.info("ProdGoodsController queryGoodsForPage end");
        return prodGoodsDTOPage;
    }

    @PostMapping(value = "/query/list")
    public List<ProdGoodsDTO> queryGoodsList(@RequestBody ProdGoodsQueryReq req) {
        log.info("ProdGoodsController queryGoodsList start");
        List<ProdGoodsDTO> result = manager.queryGoodsList(req);
        log.info("ProdGoodsController queryGoodsList end");
        return result;
    }

    @PostMapping(value = "/query/list/goodsid")
    public List<ProdGoodsDTO> queryGoodsListNoTenantId(@RequestBody ProdGoodsQueryReq req) {
        log.info("ProdGoodsController queryGoodsListNoTenantId start");
        List<ProdGoodsDTO> result = manager.queryGoodsListNoTenantId(req);
        log.info("ProdGoodsController queryGoodsList end");
        return result;
    }

    @PostMapping(value = "/goodstextinfo")
    public void modGoodsTextInfo(@RequestBody ModGoodsTextInfoReq request) throws BaseException {
        log.info("ProdGoodsController modGoodsTextInfo start");
        manager.modGoodsTextInfo(request);
        log.info("ProdGoodsController modGoodsTextInfo end");

    }

    @GetMapping(value = "/goodstextinfo/{goodsId}")
    public QryGoodsTextInfoResp queryGoodsTextInfo(@ApiParam(value = "商品ID") @PathVariable String goodsId)
        throws BaseException {
        log.info("ProdGoodsController queryGoodsTextInfo start");
        QryGoodsTextInfoResp result = manager.queryGoodsTextInfo(goodsId);
        log.info("ProdGoodsController queryGoodsTextInfo end");
        return result;
    }

    @PostMapping(path = "/prodgoods/simple/{goodsId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProdGoodsInfoResp queryProdGoodsById(@ApiParam(value = "商品ID") @PathVariable String goodsId)
        throws BaseException {
        log.info("ProdGoodsController queryProdGoodsById start");
        ProdGoodsInfoResp result = manager.queryProdGoodsById(goodsId);
        log.info("ProdGoodsController queryProdGoodsById end");
        return result;
    }

    @PostMapping(path = "/prodgoods/allstate/{goodsId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProdGoods queryProdGoodsAllStateById(@ApiParam(value = "商品ID") @PathVariable String goodsId)
        throws BaseException {
        log.info("ProdGoodsController queryProdGoodsAllStateById start");
        ProdGoods result = manager.queryProdGoodsAllStateById(goodsId);
        log.info("ProdGoodsController queryProdGoodsAllStateById end");
        return result;
    }

    @PostMapping(value = "/prodgoods/salecondition/{goodsId}")
    public List<ProdGoodsSalesConditionResp> listProdGoodsSalesConditionByGoodsId(
        @ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsController listProdGoodsSalesConditionByGoodsId start");
        List<ProdGoodsSalesConditionResp> result = manager.listProdGoodsSalesConditionByGoodsId(goodsId);
        log.info("ProdGoodsController listProdGoodsSalesConditionByGoodsId end");
        return result;
    }

    @PostMapping(value = "/prodgoods/rel/{goodsId}")
    public List<ProdGoodsRelResp> listProdGoodsRelByProdGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId)
        throws BaseException {
        log.info("ProdGoodsController listProdGoodsRelByProdGoodsId start");
        List<ProdGoodsRelResp> result = manager.listProdGoodsRelByProdGoodsId(goodsId);
        log.info("ProdGoodsController listProdGoodsRelByProdGoodsId end");
        return result;
    }

    @PostMapping(value = "/prodgoods/addbuycount")
    public ResultVO addProdBuyCount(@RequestBody List<AddProdBuyCountReq> request) {
        log.info("ProdGoodsController addProdBuyCount start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(manager::addProdBuyCount, request);
        log.info("ProdGoodsController addProdBuyCount end");
        return result;
    }

    @PostMapping("/prodgoods/reducebuycount")
    public ResultVO reduceProdBuyCount(@RequestBody List<AddProdBuyCountReq> request) {
        log.info("ProdGoodsController reduceProdBuyCount start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(manager::reduceProdBuyCount, request);
        log.info("ProdGoodsController reduceProdBuyCount end");
        return result;
    }

    @ApiOperation(value = "根据商品id查询商品名称和缩略图")
    @GetMapping(value = "/prodgoods/name/thumbnail/{goodsId}")
    public ProdGoodsDetailByIdResp getProdGoodsNameAndThumbnailById(
        @ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsWebappController getProdGoodsNameAndThumbnailById start");
        ProdGoodsDetailByIdResp result = manager.getProdGoodsNameAndThumbnailById(goodsId);
        log.info("ProdGoodsWebappController getProdGoodsNameAndThumbnailById end");
        return result;
    }

    @ApiOperation(value = "查询目标商品信息")
    @PostMapping(value = "/prodGoods/target")
    public Page<QryGoodsCatMemListResp> qryTargetGoodsList(@RequestBody ProdGoodsQueryTargetReq req)
        throws BaseException {
        log.info("ProdGoodsWebappController qryTargetGoodsList start");
        Page<QryGoodsCatMemListResp> result = manager.qryTargetGoodsList(req.getGoodsId(), req);
        log.info("ProdGoodsWebappController qryTargetGoodsList end");
        return result;
    }

    @ApiOperation(value = "查询商品内容")
    @PostMapping(value = "/goodscontent/list")
    public List<QryGoodsContentListResp> qryGoodsContentList(@RequestBody ProdGoodsQueryContentReq req)
        throws BaseException {
        log.info("ProdGoodsWebappController qryGoodsContentList start");
        List<QryGoodsContentListResp> result = manager.qryGoodsContentList(req);
        log.info("ProdGoodsWebappController qryGoodsContentList end");
        return result;
    }

    @ApiOperation(value = "修改商品内容")
    @PostMapping(value = "/goodscontent")
    public void modGoodsContent(@RequestBody ModGoodsContentReq request) throws BaseException {
        log.info("ProdGoodsWebappController modGoodsContent start");
        manager.modGoodsContent(request);
        log.info("ProdGoodsWebappController modGoodsContent end");
    }

    @ApiOperation(value = "查询可销售商品")
    @PostMapping(value = "/goods/saleable/list")
    public List<ProdGoodsDTO> querySaleableGoodsList() {
        log.info("ProdGoodsController queryAllPublishedGoodsList start");
        List<ProdGoodsDTO> result = manager.querySaleableGoodsList();
        log.info("ProdGoodsController queryAllPublishedGoodsList end");
        return result;
    }

    @ApiOperation(value = "中移智行调我们")
    @PostMapping(value = "/invoicecatg")
    public ResultVO<SynMgtInvoiceCatgNameResp> synMgtInvoiceCatgName(@RequestBody SynMgtInvoiceCatgNameReq request) {

        log.info("ProdGoodsController synMgtInvoiceCatgName start, request = [{}]", request);
        ResultVO<SynMgtInvoiceCatgNameResp> resultVO = new ResultVO();

        SynMgtInvoiceCatgNameResp synMgtInvoiceCatgNameResp = new SynMgtInvoiceCatgNameResp();
        try {
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            resultVO.setData(manager.synMgtInvoiceCatgName(request));
        }
        catch (BaseException e) {
            log.error("BaseException = [{}]", e);
            resultVO.setCode(e.getCode());
            resultVO.setMessage(e.getDesc());
            synMgtInvoiceCatgNameResp.setSyncResult("false");
            resultVO.setData(synMgtInvoiceCatgNameResp);
        }
        catch (Exception e) {
            log.error("Exception = [{}]", e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
            synMgtInvoiceCatgNameResp.setSyncResult("false");
            resultVO.setData(synMgtInvoiceCatgNameResp);
        }
        log.info("ProdGoodsController synMgtInvoiceCatgName end, resultVO = [{}]", resultVO);
        return resultVO;
    }

    @PostMapping("/prodGoods/updateSyncState/{goodsId}")
    public void updateSyncStateById(@PathVariable("goodsId") String goodsId, @RequestParam String syncState){
        manager.updateSyncStateByGoodsId(goodsId, syncState);
    }

}