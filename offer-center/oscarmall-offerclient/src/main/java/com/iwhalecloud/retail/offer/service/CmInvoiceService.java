package com.iwhalecloud.retail.offer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.iwhalecloud.retail.common.dto.CmWsResponse;
import com.iwhalecloud.retail.offer.dto.client.req.CmSyncGoodsItemInfoReq;
import com.iwhalecloud.retail.offer.dto.client.resp.CmSyncGoodsItemInfoResp;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/20 <br>
 * @see com.iwhalecloud.retail.offer.service <br>
 * @since V9.0C<br>
 */
@FeignClient(name = "${oscar.rest.invoice.name}", path = "${oscar.rest.invoice.path}")
public interface CmInvoiceService {

    @PostMapping("/syncGoodsItemInfo")
    CmWsResponse<List<CmSyncGoodsItemInfoResp>> syncGoodsItemInfo(@RequestBody CmSyncGoodsItemInfoReq request);
}
