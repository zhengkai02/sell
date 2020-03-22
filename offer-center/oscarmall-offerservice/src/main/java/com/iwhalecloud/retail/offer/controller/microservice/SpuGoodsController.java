package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddSpuGoodsReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteSpuGoods;
import com.iwhalecloud.retail.offer.dto.req.PageSpuGoods;
import com.iwhalecloud.retail.offer.dto.req.PageSpuRelGoodsReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuRelGoodsResp;
import com.iwhalecloud.retail.offer.manager.SpuGoodsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * SPU关联商品
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Api(tags = "SPU关联商品")
@Slf4j
@RestController
@RequestMapping("/offer/spu/goods")
public class SpuGoodsController {

    @Autowired
    private SpuGoodsManager spuGoodsManager;


    @ApiOperation(value = "关联商品，商品分页")
    @PostMapping(value = "/rel/page")
    public ResultVO<Page<PageSpuRelGoodsResp>> pageSpuRelGoods(@RequestBody PageSpuRelGoodsReq req) {
        log.info("SpuGoodsController pageSpuRelGoods start");
        ResultVO<Page<PageSpuRelGoodsResp>> result = ResultVOCheckUtil.buildResultVO(spuGoodsManager::pageSpuRelGoods, req);
        log.info("SpuGoodsController pageSpuRelGoods end");
        return result;
    }


    @ApiOperation(value = "新增")
    @PostMapping("/insert")
    public ResultVO addSpuGoods(@RequestBody AddSpuGoodsReq req) {
        log.info("SpuGoodsController addSpuGoods start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(spuGoodsManager::addSpuGoods, req);
        log.info("SpuGoodsController addSpuGoods end");
        return result;
    }


    @ApiOperation(value = "SPU关联商品分页")
    @PostMapping(value = "/page")
    public ResultVO<Page<PageSpuGoodsResp>> pageSpuGoods(@RequestBody PageSpuGoods req) {
        log.info("SpuGoodsController pageSpuGoods start");
        ResultVO<Page<PageSpuGoodsResp>> result = ResultVOCheckUtil.buildResultVO(spuGoodsManager::pageSpuGoods, req);
        log.info("SpuGoodsController pageSpuGoods end");
        return result;
    }


    @ApiOperation(value = "SPU关联商品删除")
    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody DeleteSpuGoods req) {
        log.info("SpuGoodsController delete start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(spuGoodsManager::deleteSpuGoods, req);
        log.info("SpuGoodsController delete end");
        return result;
    }

}
