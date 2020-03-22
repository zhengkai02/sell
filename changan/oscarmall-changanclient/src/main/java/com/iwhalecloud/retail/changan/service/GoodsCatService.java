package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.dto.resp.QryGoodsCatListResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author ZhengKai
 * @data 2019-11-11 18:56
 */
@FeignClient(name = "oscar.rest.offer.name", path = "oscar.rest.offer.path")
public interface GoodsCatService {

    @PostMapping(value = "/goodscat/detail/{catId}")
    QryGoodsCatListResp qryGoodsCatDetail(@PathVariable("catId") String catId);

    @PostMapping("/updateSyncState/{catId}")
    void updateSaleCatSyncState(@PathVariable("catId") String catId, @RequestParam("syncState") String syncState);

}
