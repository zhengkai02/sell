package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatMemListReq;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.manager.GoodsCatMemManager;
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
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/1 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since V9.0C<br>
 */
@Api(tags = "商品类目成员")
@RestController("goodsCatMemMicroService")
@RequestMapping("/offer/goodscatmem")
@Slf4j
public class GoodsCatMemController {

    @Autowired
    private GoodsCatMemManager goodsCatMemManager;


    @PostMapping("/query/list")
    public Page<QryGoodsCatMemListResp> qryGoodsCatMemList(@RequestBody QryGoodsCatMemListReq req) throws BaseException {
        log.info("GoodsCatMemController qryGoodsCatMemList start");
        Page<QryGoodsCatMemListResp> response = goodsCatMemManager.qryGoodsCatMemList(req);
        log.info("GoodsCatMemController qryGoodsCatMemList end");
        return response;
    }

    @GetMapping("/query/list/{catId}")
    public List<QryGoodsCatMemListResp> selectProdGoodsCatMemByCat(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        log.info("GoodsCatMemController selectProdGoodsCatMemByCat start");
        List<QryGoodsCatMemListResp> response = goodsCatMemManager.selectProdGoodsCatMemByCat(catId);
        log.info("GoodsCatMemController selectProdGoodsCatMemByCat end");
        return response;
    }

    @PostMapping("/list")
    public int batchDeleteGoodsMem(@RequestBody List<String> offerIds) throws BaseException {
        log.info("GoodsCatMemController batchDeleteGoodsMem start");
        int result = goodsCatMemManager.batchDeleteGoodsMem(offerIds);
        log.info("GoodsCatMemController batchDeleteGoodsMem end");
        return result;
    }

    @PostMapping("/update/{catId}")
    public void updateGoodsMem(@ApiParam(value = "目录ID") @PathVariable String catId, @RequestBody List<String> offerIds) throws BaseException {
        log.info("GoodsCatMemController updateGoodsMem start");
        goodsCatMemManager.updateGoodsMem(catId, offerIds);
        log.info("GoodsCatMemController updateGoodsMem end");
    }

}
