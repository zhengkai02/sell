package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgMemQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgMemResp;
import com.iwhalecloud.retail.offer.manager.TblStoreCatgMemManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @ClassName TblStoreCatgMemController
 * @Author wangzhongbao
 * @Date 2019/4/29 15:26
 **/
@Api(tags = "店铺管理类目")
@Slf4j
@RestController
@RequestMapping("/offer/store/tblstorecatgmem")
public class TblStoreCatgMemController {

    @Autowired
    private TblStoreCatgMemManager tblStoreCatgMemManager;

    @PostMapping("/list")
    public Page<TblStoreCatgMemResp> qryStoreCatgMem(@RequestBody TblStoreCatgMemQryReq req) throws BaseException {
        return tblStoreCatgMemManager.qryStoreCatgMem(req);
    }

}
