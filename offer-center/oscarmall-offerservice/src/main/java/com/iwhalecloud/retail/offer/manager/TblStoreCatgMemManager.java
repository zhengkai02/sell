package com.iwhalecloud.retail.offer.manager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgMemQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgMemResp;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMemMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @ClassName TblStoreCatgMemManager
 * @Author wangzhongbao
 * @Date 2019/4/29 15:27
 **/
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TblStoreCatgMemManager {

    @Autowired
    private TblStoreCatgMemMapper tblStoreCatgMemMapper;

    public Page<TblStoreCatgMemResp> qryStoreCatgMem(TblStoreCatgMemQryReq req) throws BaseException {
        log.info("TblStoreCatgMemManager qryStoreCatgMem start, request = [{}]", req);

        if (null == req || null == req.getPageNo() || null == req.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(req.getStoreId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        Page<TblStoreCatgMemResp> page = new Page<>(req.getPageNo(), req.getPageSize());

        Page<TblStoreCatgMemResp> respList = tblStoreCatgMemMapper.qryStoreCatgMem(page, req);

        log.info("TblStoreCatgMemManager qryStoreCatgMem end");
        return respList;
    }
}
