package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.dto.client.req.QueryGoodsPriceReq;
import com.iwhalecloud.retail.offer.entity.GoodsPrice;
import com.iwhalecloud.retail.offer.entity.PriceFactor;
import com.iwhalecloud.retail.offer.manager.GoodsPriceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huminghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/8 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since V9.0C<br>
 */
@Api(tags = "商品价格")
@Slf4j
@RestController
@RequestMapping("/offer/goodsprice")
public class GoodsPriceController {

    @Autowired
    private GoodsPriceManager goodsPriceManager;

    @GetMapping("/pricefactor/list")
    public List<PriceFactor> qryGoodsPriceFactorList() {
        log.info("GoodsPriceController qryGoodsPriceFactorList start");
        List<PriceFactor> priceFactors = goodsPriceManager.qryGoodsPriceFactorList();
        log.info("GoodsPriceController qryGoodsPriceFactorList end");
        return priceFactors;
    }

    @PostMapping("/list")
    public List<GoodsPrice> qryGoodsPriceList(@RequestBody QueryGoodsPriceReq req) {
        log.info("GoodsPriceController qryGoodsPriceList start");
        List<GoodsPrice> goodsPrices = goodsPriceManager.qryGoodsPriceList(req);
        log.info("GoodsPriceController qryGoodsPriceList end");
        return goodsPrices;
    }

    @PostMapping("/add")
    public GoodsPrice addGoodsPrice(@RequestBody GoodsPrice req) {
        log.info("GoodsPriceController addGoodsPrice start.");
        GoodsPrice resp = goodsPriceManager.addGoodsPrice(req);
        log.info("GoodsPriceController addGoodsPrice end.");
        return resp;
    }

    @PostMapping("/update/{goodsPriceId}")
    public GoodsPrice modGoodsPrice(@ApiParam(value = "商品价格ID") @PathVariable String goodsPriceId, @RequestBody GoodsPrice req) {
        log.info("GoodsPriceController modGoodsPrice start.");
        GoodsPrice resp = goodsPriceManager.modGoodsPrice(goodsPriceId, req);
        log.info("GoodsPriceController modGoodsPrice end.");
        return resp;
    }

    @PostMapping("/delprice/{goodsPriceId}")
    public void delGoodsPrice(@ApiParam(value = "商品价格ID") @PathVariable String goodsPriceId) {
        log.info("GoodsPriceController delGoodsPrice start.");
        goodsPriceManager.delGoodsPrice(goodsPriceId);
        log.info("GoodsPriceController delGoodsPrice end.");
    }
}
