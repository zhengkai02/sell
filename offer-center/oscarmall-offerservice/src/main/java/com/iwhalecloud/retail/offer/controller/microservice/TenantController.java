package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddManagementCatReq;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.offer.dto.resp.TenantDetailResp;
import com.iwhalecloud.retail.offer.manager.TenantManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * 租户
 *
 * @author fanxiaofei
 * @date 2019-05-22
 */
@Api(tags = "租户")
@Slf4j
@RestController
@RequestMapping("/offer/tenant")
public class TenantController {

    @Autowired
    private TenantManager tenantManager;


    @ApiOperation(value = "租户详情")
    @PostMapping(value = "/detail")
    public ResultVO<TenantDetailResp> queryTenantDetail(@ApiParam(value = "租户ID") @RequestParam String tenantId) {
        log.info("TenantController queryTenantDetail start");
        ResultVO<TenantDetailResp> result = ResultVOCheckUtil.buildResultVO(tenantManager::queryTenantDetail, tenantId);
        log.info("TenantController queryTenantDetail end");
        return result;
    }


    @ApiOperation(value = "租户所有可使用的目录标识")
    @PostMapping("/catalog/management/cat")
    public ResultVO<ArrayList<String>> listManagementCatIdByTenantId(@ApiParam(value = "租户ID") @RequestParam String tenantId) {
        log.info("TenantController listManagementCatIdByTenantId start");
        ResultVO<ArrayList<String>> result = ResultVOCheckUtil.buildResultVO(tenantManager::listManagementCatIdByTenantId, tenantId);
        log.info("TenantController listManagementCatIdByTenantId end");
        return result;
    }


    @ApiOperation(value = "租户所有可使用的商品目录")
    @PostMapping("/catalog/management/list")
    public ResultVO<ArrayList<QryGoodsCatListResp>> listManagementCatByTenantId(@ApiParam(value = "租户ID") @RequestParam String tenantId) {
        log.info("TenantController listManagementCatByTenantId start");
        ResultVO<ArrayList<QryGoodsCatListResp>> result = ResultVOCheckUtil.buildResultVO(tenantManager::listManagementCatByTenantId, tenantId);
        log.info("TenantController listManagementCatByTenantId end");
        return result;
    }


    @ApiOperation(value = "新增租户可使用的商品目录")
    @PostMapping("/catalog/management/insert")
    public ResultVO addManagementCat(@RequestBody AddManagementCatReq req) {
        log.info("TenantController addManagementCat start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(tenantManager::addManagementCat, req);
        log.info("TenantController addManagementCat end");
        return result;
    }

}
