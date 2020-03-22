package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddSpuReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteSpuReq;
import com.iwhalecloud.retail.offer.dto.req.PageSpuReq;
import com.iwhalecloud.retail.offer.dto.req.UpdateSpuReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuResp;
import com.iwhalecloud.retail.offer.manager.SpuManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * SPU
 * @author fanxiaofei
 * @date 2019-05-24
 */
@Api(tags = "SPU")
@Slf4j
@RestController
@RequestMapping("/offer/spu")
public class SpuController {

    @Autowired
    private SpuManager spuManager;


    @ApiOperation(value = "新增")
    @PostMapping("/insert")
    public ResultVO addSpu(@RequestBody AddSpuReq req) {
        log.info("SpuController addSpu start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(spuManager::addSpu, req);
        log.info("SpuController addSpu end");
        return result;
    }


    @ApiOperation(value = "分页")
    @PostMapping(value = "/page")
    public ResultVO<Page<PageSpuResp>> pageSpu(@RequestBody PageSpuReq req) {
        log.info("SpuController pageSpu start");
        ResultVO<Page<PageSpuResp>> result = ResultVOCheckUtil.buildResultVO(spuManager::pageSpu, req);
        log.info("SpuController pageSpu end");
        return result;
    }


    @ApiOperation(value = "修改")
    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody UpdateSpuReq req) {
        log.info("SpuController update start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(spuManager::update, req);
        log.info("SpuController update end");
        return result;
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody DeleteSpuReq req) {
        log.info("SpuController delete start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(spuManager::delete, req);
        log.info("SpuController delete end");
        return result;
    }

}
