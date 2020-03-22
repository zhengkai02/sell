package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.dto.resp.ProdGoodsDetailByIdResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhengKai
 * @data 2019-11-19 11:34
 */
@FeignClient(name = "${oscar.rest.offer.name}", path = "${oscar.rest.offer.path}")
public interface ProdGoodsService {

    @RequestMapping(value = "/prodgoods/{goodsId}", method = RequestMethod.GET)
    ProdGoodsDetailByIdResp queryProdGoodsDetailById(@PathVariable(value = "goodsId") String goodsId);

    @PostMapping(value = "/prodGoods/updateSyncState/{goodsId}")
    void updateGoodsSyncState(@PathVariable("goodsId") String goodsId, @RequestParam("syncState") String syncState);
}
