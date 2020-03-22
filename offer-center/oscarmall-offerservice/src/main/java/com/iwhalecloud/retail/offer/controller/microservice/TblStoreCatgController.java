package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.AddTblStoreCatgReq;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgQryReq;
import com.iwhalecloud.retail.offer.dto.resp.AddTblStoreCatgResp;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgResp;
import com.iwhalecloud.retail.offer.manager.TblStoreCatgManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @ClassName TblStoreCatgController
 * @Author wangzhongbao
 * @Date 2019/4/28 15:33
 **/
@Api(tags = "店铺目录")
@Slf4j
@RestController
@RequestMapping("/offer/store/tblstorecatg")
public class TblStoreCatgController {

    @Autowired
    private TblStoreCatgManager tblStoreCatgManager;


    @PostMapping("/create")
    public AddTblStoreCatgResp createStoreCatg(@RequestBody AddTblStoreCatgReq req) throws BaseException {
        return tblStoreCatgManager.create(req);
    }

    @PostMapping("/update/{catId}")
    public AddTblStoreCatgResp updateStoreCatg(@RequestBody AddTblStoreCatgReq req, @ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        return tblStoreCatgManager.updateStoreCatg(req, catId);
    }

    @PostMapping("/del/{catId}")
    public AddTblStoreCatgResp delStoreCatg(@ApiParam(value = "目录ID") @PathVariable String catId) throws BaseException {
        return tblStoreCatgManager.delStoreCatg(catId);
    }

    @PostMapping("/list")
    public List<TblStoreCatgResp> qryStoreCatg(@RequestBody TblStoreCatgQryReq req) throws BaseException {
        return tblStoreCatgManager.qryStoreCatg(req);
    }

    @PostMapping("/detail/{catgId}")
    public TblStoreCatgResp qryStoreCatg(@ApiParam(value = "目录ID") @PathVariable String catgId) throws BaseException {
        return tblStoreCatgManager.qryStoreCatgDetail(catgId);
    }

    @PostMapping("/updateSyncState/{catId}")
    public void updateSyncStateByCatId(@PathVariable("catId") String catId, @RequestParam("syncState") String syncState) {
        tblStoreCatgManager.updateSyncStateByCatId(catId, syncState);
    }

}
