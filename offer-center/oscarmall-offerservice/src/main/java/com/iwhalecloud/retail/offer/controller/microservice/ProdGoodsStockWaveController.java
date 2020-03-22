package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsStockWaveReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockQtyResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockWaveResp;
import com.iwhalecloud.retail.offer.manager.ProdGoodsStockWaveManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 自动库存上下架
 *
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Api(tags = "自动库存上下架")
@Slf4j
@RestController("prodGoodsStockWaveMicroService")
@RequestMapping("/offer/stockwave")
public class ProdGoodsStockWaveController {

    @Autowired
    private ProdGoodsStockWaveManager prodGoodsStockWaveManager;


    @ApiOperation(value = "新增库存上下架")
    @PostMapping(value = "/create")
    public ProdGoodsStockWaveResp create(@RequestBody ProdGoodsStockWaveReq req) throws BaseException {
        log.info("ProdGoodsStockWaveController create start");
        ProdGoodsStockWaveResp result = prodGoodsStockWaveManager.create(req);
        log.info("ProdGoodsStockWaveController create end");
        return result;
    }


    @ApiOperation(value = "删除库存上下架")
    @PostMapping(value = "/delete/{stockWaveId}")
    public void delete(@ApiParam(value = "上架ID") @PathVariable String stockWaveId, @ApiParam(value = "用户ID") @RequestParam Long userId) throws BaseException {
        log.info("ProdGoodsStockWaveController delete start");
        prodGoodsStockWaveManager.delete(stockWaveId, userId);
        log.info("ProdGoodsStockWaveController delete end");
    }


    @ApiOperation(value = "修改库存上下架")
    @PostMapping(value = "/update/{stockWaveId}")
    public void edit(@ApiParam(value = "上架ID") @PathVariable String stockWaveId, @RequestBody ProdGoodsStockWaveReq req) throws BaseException {
        log.info("ProdGoodsStockWaveController update start");
        prodGoodsStockWaveManager.update(stockWaveId, req);
        log.info("ProdGoodsStockWaveController update end");
    }


    @ApiOperation(value = "根据商品id查询库存上下架")
    @GetMapping(value = "/get/{goodsId}")
    public List<ProdGoodsStockWaveResp> getProdGoodsStockWaveByGoodsId(@ApiParam(value = "商品ID") @PathVariable String goodsId) throws BaseException {
        log.info("ProdGoodsStockWaveController getProdGoodsStockWaveByGoodsId start");
        List<ProdGoodsStockWaveResp> result = prodGoodsStockWaveManager.getProdGoodsStockWaveByGoodsId(goodsId);
        log.info("ProdGoodsStockWaveController getProdGoodsStockWaveByGoodsId end");
        return result;
    }

    @RequestMapping(value = "stockqty/{goodsId}", method = RequestMethod.GET)
    public ProdGoodsStockQtyResp qryStockQty(@ApiParam(value = "商品ID") @PathVariable("goodsId") String goodsId) throws BaseException {
        log.info("ProdGoodsStockWaveController getProdGoodsStockWaveByGoodsId start");
        ProdGoodsStockQtyResp result = prodGoodsStockWaveManager.qryStockQty(goodsId);
        log.info("ProdGoodsStockWaveController getProdGoodsStockWaveByGoodsId end");
        return result;
    }

}
