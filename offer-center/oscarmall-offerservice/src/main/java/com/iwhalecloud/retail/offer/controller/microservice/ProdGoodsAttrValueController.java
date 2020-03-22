package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsAttrValueReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrValueResp;
import com.iwhalecloud.retail.offer.manager.ProdGoodsAttrValueManager;
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
 * 商品属性
 *
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Api(tags = "商品属性")
@Slf4j
@RestController("prodGoodsAttrValueMicroService")
@RequestMapping("/offer/prodgoodsattrvalue")
public class ProdGoodsAttrValueController {

    @Autowired
    private ProdGoodsAttrValueManager prodGoodsAttrValueManager;


    @ApiOperation(value = "新增商品属性")
    @PostMapping(value = "/create")
    public void create(@RequestBody List<ProdGoodsAttrValueReq> req) throws BaseException {
        log.info("ProdGoodsAttrValueController create start");
        prodGoodsAttrValueManager.create(req);
        log.info("ProdGoodsAttrValueController create end");
    }


    @ApiOperation(value = "删除商品属性")
    @PostMapping(value = "/delete/{goodsId}/{attrId}")
    public void delete(@ApiParam(value = "商品ID") @PathVariable String goodsId, @ApiParam(value = "属性ID") @PathVariable String attrId) throws BaseException {
        log.info("ProdGoodsAttrValueController delete start");
        prodGoodsAttrValueManager.delete(goodsId, attrId);
        log.info("ProdGoodsAttrValueController delete end");
    }


    @ApiOperation(value = "修改商品属性")
    @PostMapping(value = "/update/{goodsId}/{attrId}")
    public void update(@ApiParam(value = "商品ID") @PathVariable String goodsId, @ApiParam(value = "属性ID") @PathVariable String attrId, @RequestBody ProdGoodsAttrValueReq req) throws BaseException {
        log.info("ProdGoodsAttrValueController update start");
        prodGoodsAttrValueManager.update(goodsId, attrId, req);
        log.info("ProdGoodsAttrValueController update end");
    }


    @ApiOperation(value = "根据商品id查询属性值")
    @PostMapping(value = "/get/{goodsId}")
    public List<AttrValueResp> getProdGoodsAttrValueByGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsAttrValueController getProdGoodsAttrValueByGoodsId start");
        List<AttrValueResp> result = prodGoodsAttrValueManager.getProdGoodsAttrValueByGoodsId(goodsId);
        log.info("ProdGoodsAttrValueController getProdGoodsAttrValueByGoodsId end");
        return result;
    }

}
