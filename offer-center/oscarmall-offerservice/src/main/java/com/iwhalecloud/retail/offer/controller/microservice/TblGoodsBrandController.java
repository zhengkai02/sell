package com.iwhalecloud.retail.offer.controller.microservice;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandAddReq;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandModReq;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandAddResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.manager.TblGoodsBrandManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandController
 * @Author wangzhongbao
 * @Date 2019/5/13 10:06
 **/
@Api(tags = "商品品牌")
@Slf4j
@RestController("tblGoodsBrandController")
@RequestMapping("/offer/tblgoodsbrand")
public class TblGoodsBrandController {

    @Autowired
    private TblGoodsBrandManager tblGoodsBrandManager;

    @PostMapping("/create")
    public TblGoodsBrandAddResp addGoodsBrand(@RequestBody TblGoodsBrandAddReq req) throws BaseException {
        return tblGoodsBrandManager.addGoodsBrand(req);
    }

    @PostMapping("/list")
    public Page<TblGoodsBrandQryResp> qryGoodsBrandList(@RequestBody TblGoodsBrandQryReq req) throws BaseException {
        return tblGoodsBrandManager.qryGoodsBrandList(req);
    }

    @PostMapping("/mod/{brandId}")
    public int updateGoodsBrand(@ApiParam(value = "品牌ID") @PathVariable String brandId, @RequestBody TblGoodsBrandModReq req) throws BaseException {
        return tblGoodsBrandManager.updateGoodsBrand(brandId, req);
    }

    @PostMapping("/btachdel")
    public int deleteGoodsBrand(@RequestBody List<String> brandIds) throws BaseException {
        return tblGoodsBrandManager.deleteGoodsBrand(brandIds);
    }

    @PostMapping("")
    public List<TblGoodsBrandQryResp> qryAllGoodsBrandList() throws BaseException {
        return tblGoodsBrandManager.qryAllGoodsBrandList();
    }

    @PostMapping("/{catgId}/goodsbrand")
    public List<TblGoodsBrandQryResp> qryGoodsBrandInCatg(@ApiParam(value = "目录ID") @PathVariable String catgId) throws BaseException {
        return tblGoodsBrandManager.qryGoodsBrandInCatg(catgId);
    }

    @PostMapping("/list/tenant")
    public List<TblGoodsBrandTenantResp> qryGoodsBrandByTenantId() throws BaseException {
        log.info("TblGoodsBrandController qryGoodsBrandByTenantId start");
        List<TblGoodsBrandTenantResp> resp = tblGoodsBrandManager.qryGoodsBrandByTenantId();
        log.info("TblGoodsBrandController qryGoodsBrandByTenantId end");
        return resp;
    }

}
