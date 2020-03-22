package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.RightsReq;
import com.iwhalecloud.retail.offer.dto.resp.RightsListResp;
import com.iwhalecloud.retail.offer.manager.RightsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 权益
 * H5调用亚信接口
 * @author fanxiaofei
 * @date 2019-03-19
 */
@Api(tags = "权益")
@Slf4j
@RestController
@RequestMapping("/offer/rights")
public class RightsController {

    @Autowired
    private RightsManager rightsManager;


    @ApiOperation(value = "查询用户权益")
    @PostMapping("")
    public ResultVO<RightsListResp> queryRights(@RequestBody RightsReq req) {
        log.info("RightController queryRights start");
        ResultVO<RightsListResp> result = ResultVOCheckUtil.buildResultVO(rightsManager::queryRights, req);
        log.info("RightController queryRights end");
        return result;
    }

}
