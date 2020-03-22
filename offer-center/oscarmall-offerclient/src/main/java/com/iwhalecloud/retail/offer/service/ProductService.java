package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.req.QueryProductListReq;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryProductListResp;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 产品
 * @author fanxiaofei
 * @date 2019-03-13
 */
@FeignClient(name = "${sc.rest.product.name}", path = "${sc.rest.product.path}")
public interface ProductService {

    /**
     * 产品列表查询
     * 调用亚信
     * @param req QueryProductListReq
     * @return QueryProductListResp
     */
    @PostMapping("/product_list")
    ResultVO<QueryProductListResp> listProduct(@RequestBody QueryProductListReq req);

}