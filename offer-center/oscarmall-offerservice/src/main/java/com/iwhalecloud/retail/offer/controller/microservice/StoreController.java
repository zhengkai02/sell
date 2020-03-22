package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.AddStoreReq;
import com.iwhalecloud.retail.offer.dto.req.ModStoreStateReq;
import com.iwhalecloud.retail.offer.dto.req.QryStoreReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreByOrgIdReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreByOrgIdResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreDetailResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreRuleResp;
import com.iwhalecloud.retail.offer.manager.StoreManager;
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
import java.util.List;


/**
 * 店铺
 *
 * @author fanxiaofei
 * @date 2019-04-29
 */
@Api(tags = "店铺")
@Slf4j
@RestController
@RequestMapping("/offer")
public class StoreController {

    @Autowired
    private StoreManager storeManager;


    @ApiOperation(value = "新增店铺")
    @PostMapping(value = "/store/insert")
    public ResultVO insert(@RequestBody AddStoreReq req) {
        log.info("StoreController insert start, AddStoreReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::insert, req);
        log.info("StoreController insert end");
        return result;
    }


    @ApiOperation(value = "查询所有")
    @PostMapping(value = "/store/all")
    public List<QueryStoreResp> queryStore() {
        log.info("StoreController queryStore start");
        List<QueryStoreResp> result = storeManager.queryStore();
        log.info("StoreController queryStore end");
        return result;
    }


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/store/page")
    public Page<QueryStoreResp> queryStorePage(@RequestBody QueryStoreReq req) {
        log.info("StoreController queryStorePage start, QueryStoreReq = [{}]", req);
        Page<QueryStoreResp> result = storeManager.queryStorePage(req);
        log.info("StoreController queryStorePage end");
        return result;
    }

    @ApiOperation(value = "根据条件查询店铺信息，供其他中心补全sql使用")
    @PostMapping(value = "/store/list")
    public ResultVO<ArrayList<QueryStoreResp>> qryStoreByCond(@RequestBody QryStoreReq req) throws BaseException {
        log.info("StoreController qryStoreByCond start, QueryStoreReq = [{}]", req);
        ResultVO<ArrayList<QueryStoreResp>> result = ResultVOCheckUtil.buildResultVO(storeManager::qryStoreByCond, req);
        log.info("StoreController qryStoreByCond end");
        return result;
    }


    @ApiOperation(value = "店铺详情")
    @PostMapping(value = "/store/detail")
    public ResultVO<QueryStoreDetailResp> queryStoreDetail(@ApiParam(value = "店铺ID") @RequestParam String storeId) {
        log.info("StoreController queryStoreDetail start, storeId = [{}]", storeId);
        ResultVO<QueryStoreDetailResp> result = ResultVOCheckUtil.buildResultVO(storeManager::queryStoreDetail, storeId);
        log.info("StoreController queryStoreDetail end");
        return result;
    }


    @ApiOperation(value = "根据机构id查询店铺详情")
    @PostMapping(value = "/store/detail/org")
    public ResultVO<QueryStoreDetailResp> queryStoreDetailByOrgId(@ApiParam(value = "机构ID") @RequestParam String orgId) {
        log.info("StoreController queryStoreDetailByOrgId start, orgId = [{}]", orgId);
        ResultVO<QueryStoreDetailResp> result = ResultVOCheckUtil.buildResultVO(storeManager::queryStoreDetailByOrgId, orgId);
        log.info("StoreController queryStoreDetailByOrgId end");
        return result;
    }


    @ApiOperation(value = "关店")
    @PostMapping(value = "/store/delete")
    public ResultVO delete(@RequestBody AddStoreReq req) {
        log.info("StoreController delete start, AddStoreReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::delete, req);
        log.info("StoreController delete end");
        return result;
    }


    @ApiOperation(value = "编辑")
    @PostMapping(value = "/store/update")
    public ResultVO update(@RequestBody AddStoreReq req) {
        log.info("StoreController update start, AddStoreReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::update, req);
        log.info("StoreController update end");
        return result;
    }


    @ApiOperation(value = "认证")
    @PostMapping(value = "/store/authentication")
    public ResultVO authentication(@RequestBody AddStoreReq req) {
        log.info("StoreController authentication start, AddStoreReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::authentication, req);
        log.info("StoreController authentication end");
        return result;
    }


    @ApiOperation(value = "提交认证请求")
    @PostMapping(value = "/store/authentication/request")
    public ResultVO submitAuthRequest(@RequestBody AddStoreReq req) {
        log.info("StoreController submitAuthRequest start, AddStoreReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::submitAuthRequest, req);
        log.info("StoreController submitAuthRequest end");
        return result;
    }


    @ApiOperation(value = "查询商品销售规则")
    @PostMapping(value = "/store/storerule/list")
    public List<QueryStoreRuleResp> qryAllStoreRuleList() {
        log.info("StoreController qryStoreRuleList start");
        List<QueryStoreRuleResp> result = storeManager.qryAllStoreRuleList();
        log.info("StoreController qryStoreRuleList end");
        return result;
    }


    @ApiOperation(value = "修改状态")
    @PostMapping(value = "/store/mod/banned")
    public ResultVO modStoreState(@RequestBody ModStoreStateReq req) {
        log.info("StoreController modStoreState start, ModStoreStateReq = [{}]", req);
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::modStoreState, req);
        log.info("StoreController modStoreState end");
        return result;
    }


    @ApiOperation(value = "根据机构Id查询关联的店铺列表(亚信调用)")
    @PostMapping(value = "/qryStoreByOrgId")
    public ResultVO<ArrayList<QueryStoreByOrgIdResp>> qryStoreByOrgId(@RequestBody QueryStoreByOrgIdReq req) {
        log.info("StoreController qryStoreByOrgId start");
        ResultVO<ArrayList<QueryStoreByOrgIdResp>> result = ResultVOCheckUtil.buildResultVO(storeManager::qryStoreByOrgId, req);
        log.info("StoreController qryStoreByOrgId end");
        return result;
    }

    @ApiOperation(value = "审批店铺")
    @PostMapping("/store/audit")
    public ResultVO auditStore(@RequestBody AddStoreReq req) throws BaseException {
        log.info("StoreController auditStore start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(storeManager::auditStore, req);
        log.info("StoreController auditStore end");
        return result;
    }
}
