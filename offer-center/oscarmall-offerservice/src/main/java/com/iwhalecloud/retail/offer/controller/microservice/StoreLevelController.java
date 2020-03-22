package com.iwhalecloud.retail.offer.controller.microservice;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddStoreLevelReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteStoreLevelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreLevelReq;
import com.iwhalecloud.retail.offer.manager.StoreLevelManager;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.ApiOperation;


/**
 * 店铺级别
 * @author fanxiaofei
 * @date 2019-04-28
 */
@Api(tags = "店铺级别")
@Slf4j
@RestController
@RequestMapping("/offer/store/storelevel")
public class StoreLevelController {

    @Autowired
    private StoreLevelManager storeLevelManager;



    @ApiOperation(value = "新增")
    @PostMapping(value = "/insert")
    public ResultVO<AddStoreLevelReq> create(@RequestBody AddStoreLevelReq req) {
        log.info("StoreLevelController create start, req = [{}]", req);
        ResultVO<AddStoreLevelReq> result = ResultVOCheckUtil.buildResultVO(storeLevelManager::create, req);
        log.info("StoreLevelController create end, result = [{}]", result);
        return result;
    }


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/page")
    public Page<AddStoreLevelReq> queryStoreListPageByLevelName(@RequestBody QueryStoreLevelReq req) {
        log.info("StoreLevelController queryStoreListPageByLevelName start, QueryStoreLevelReq = [{}]", req);
        Page<AddStoreLevelReq> result = storeLevelManager.queryStoreListPageByLevelName(req);
        log.info("StoreLevelController queryStoreListPageByLevelName end");
        return result;
    }


    @ApiOperation(value = "修改")
    @PostMapping(value = "/update")
    public ResultVO<AddStoreLevelReq> update(@RequestBody AddStoreLevelReq req) {
        log.info("StoreLevelController update start, AddStoreLevelReq = [{}]", req);
        ResultVO<AddStoreLevelReq> result = ResultVOCheckUtil.buildResultVO(storeLevelManager::update, req);
        log.info("StoreLevelController update end , result = [{}]", result);
        return result;
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody DeleteStoreLevelReq req) {
        log.info("StoreLevelController delete start, DeleteStoreLevelReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeLevelManager::delete, req);
        log.info("StoreLevelController delete end");
        return result;
    }

}
