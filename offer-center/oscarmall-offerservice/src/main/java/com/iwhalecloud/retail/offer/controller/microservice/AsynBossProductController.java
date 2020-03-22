package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.client.req.QueryProductListReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryProductObjResp;
import com.iwhalecloud.retail.offer.manager.AsynBossProductManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 产品
 * 调用亚信
 * @author fanxiaofei
 * @date 2019/03/13
 */
@Api(tags = "产品")
@Slf4j
@CrossOrigin
@RestController("asynBossProductMicroService")
@RequestMapping("/offer/product")
public class AsynBossProductController {

    @Autowired
    private AsynBossProductManager asynBossProductManager;

    @ApiOperation(value = "产品列表查询 调用亚信")
    @PostMapping(value = "/list")
    public ResultVO<QueryProductObjResp> listProduct(@RequestBody QueryProductListReq req) throws BaseException {
        log.info("AsynBossProductController listProduct start");
        ResultVO<QueryProductObjResp> result = asynBossProductManager.listProduct(req);
        log.info("AsynBossProductController listProduct end");
        return result;
    }

}