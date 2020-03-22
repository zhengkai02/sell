package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.req.UpdateProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesRuleResp;
import com.iwhalecloud.retail.offer.manager.ProdGoodsSalesConditionManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品适用规则
 *
 * @author fanxiaofei
 * @date 2019/03/10
 */
@Api(tags = "商品适用规则")
@Slf4j
@RestController("prodGoodsSalesConditionMicroService")
@RequestMapping("/offer/prodgoodssalescondition")
public class ProdGoodsSalesConditionController {

    @Autowired
    private ProdGoodsSalesConditionManager prodGoodsSalesConditionManager;


    @ApiOperation(value = "新增商品适用规则")
    @PostMapping(value = "/create")
    public void save(@RequestBody ProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionController create start");
        prodGoodsSalesConditionManager.create(req);
        log.info("ProdGoodsSalesConditionController create end");
    }


    @ApiOperation(value = "删除商品适用规则")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody ProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionController delete start");
        prodGoodsSalesConditionManager.delete(req);
        log.info("ProdGoodsSalesConditionController delete end");
    }


    @ApiOperation(value = "修改商品适用规则")
    @PostMapping(value = "/update/{goodsId}")
    public void update(@ApiParam(value = "商品ID") @PathVariable String goodsId, @RequestBody UpdateProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionController update start");
        prodGoodsSalesConditionManager.update(goodsId, req);
        log.info("ProdGoodsSalesConditionController update end");
    }


    @ApiOperation(value = "根据商品id查询商品销售规则")
    @PostMapping(value = "/{goodsId}")
    public List<ProdGoodsSalesRuleResp> listProdGoodsSalesConditionByProdGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsSalesConditionController ListProdGoodsSalesConditionByProdGoodsId start");
        List<ProdGoodsSalesRuleResp> result = prodGoodsSalesConditionManager.listProdGoodsSalesConditionByProdGoodsId(goodsId);
        log.info("ProdGoodsSalesConditionController ListProdGoodsSalesConditionByProdGoodsId end");
        return result;
    }

}
