package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.dto.resp.TblStoreCatgResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZhengKai
 * @data 2019-11-19 10:35
 */
@FeignClient(name = "${oscar.rest.offer.name}", path = "${oscar.rest.offer.path}")
public interface TblStoreCatgService {

    @PostMapping("/store/tblstorecatg/detail/{catgId}")
    TblStoreCatgResp qryStoreCatgDetail(@PathVariable(value = "catgId") String catgId);

    @PostMapping("/updateSyncState/{catId}")
    public void updateStoreCatSyncState(@PathVariable("catId") String catId, @RequestParam("syncState") String syncState);
}
