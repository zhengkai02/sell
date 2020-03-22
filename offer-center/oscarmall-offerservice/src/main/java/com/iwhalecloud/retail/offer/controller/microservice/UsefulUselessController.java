package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.QueryUsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.req.UsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryUsefulUselessResp;
import com.iwhalecloud.retail.offer.manager.UsefulUselessManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 赞踩
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Api(tags = "赞踩")
@Slf4j
@RestController
@RequestMapping("/offer/evaluation/usefuluseless")
public class UsefulUselessController {

    @Autowired
    private UsefulUselessManager usefulUselessManager;


    @ApiOperation(value = "查询用户是否赞踩过评论或文章")
    @PostMapping(value = "/query")
    public ResultVO<QueryUsefulUselessResp> query(@RequestBody QueryUsefulUselessReq req) {
        log.info("UsefulUselessController query start");
        ResultVO<QueryUsefulUselessResp> result = ResultVOCheckUtil.buildResultVO(usefulUselessManager::query, req);
        log.info("UsefulUselessController query end");
        return result;
    }


    @ApiOperation(value = "赞踩评价和文章")
    @PostMapping(value = "/action")
    public ResultVO action(@RequestBody UsefulUselessReq req) {
        log.info("UsefulUselessController action start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(usefulUselessManager::action, req);
        log.info("UsefulUselessController action end");
        return result;
    }

}
