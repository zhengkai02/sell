package com.iwhalecloud.retail.offer.manager;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.StoreLevelStateDef;
import com.iwhalecloud.retail.offer.dto.req.AddStoreLevelReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteStoreLevelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreLevelReq;
import com.iwhalecloud.retail.offer.entity.StoreLevel;
import com.iwhalecloud.retail.offer.mapper.StoreLevelMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 店铺等级
 * @author fanxiaofei
 * @date 2019/04/28
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StoreLevelManager {


    @Autowired
    private StoreLevelMapper storeLevelMapper;


    /**
     * 新增店铺等级
     * @param req AddStoreLevelReq
     */
    public AddStoreLevelReq create(AddStoreLevelReq req) throws BaseException {
        log.info("StoreLevelManager create start, AddStoreLevelReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getLevelName(), OfferBaseMessageCodeEnum.STORE_LEVEL_NAME_IS_NULL);
        AssertUtil.isNotNull(req.getCreateBy(), OfferBaseMessageCodeEnum.CREATED_STAFF_ID_IS_ERROR);
        // 校验名称唯一
        AddStoreLevelReq addStoreLevelReq = storeLevelMapper.queryByLevelName(req.getLevelName());
        if (null != addStoreLevelReq) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_LEVEL_NAME_IS_EXIST);
        }

        StoreLevel storeLevel = new StoreLevel();
        BeanUtils.copyProperties(req, storeLevel);
        Date date = DBDateUtil.getCurrentDBDateTime();
        storeLevel.setStateDate(date);
        storeLevel.setState(StoreLevelStateDef.STATE_A);
        storeLevel.setStoreLevelId(UidGeneator.getUIDStr());
        storeLevel.setCreateTime(date);
        storeLevel.setCreateBy(req.getCreateBy());
        storeLevel.setModifyBy(req.getCreateBy());
        storeLevel.setModifyTime(date);
        storeLevelMapper.insert(storeLevel);

        log.info("StoreLevelManager create end");
        return req;
    }


    /**
     * 分页查询
     * @param req QueryStoreLevelReq
     * @return Page<AddStoreLevelReq>
     */
    public Page<AddStoreLevelReq> queryStoreListPageByLevelName(@RequestParam QueryStoreLevelReq req) {
        log.info("StoreLevelManager queryStoreListPageByLevelName start, QueryStoreLevelReq = [{}]", req);

        Integer pageSize = req.getPageSize();
        final Integer size = 20;
        if (null == pageSize) {
            pageSize = size;
        }
        Integer pageNo = req.getPageNo();
        if (null == pageNo) {
            pageNo = 1;
        }
        Page<AddStoreLevelReq> page = new Page<>(pageNo, pageSize);
        Page<AddStoreLevelReq> result = storeLevelMapper.queryStoreLevelPage(page, req);

        log.info("StoreLevelManager queryStoreListPageByLevelName end, result = [{}]", result);
        return result;
    }


    /**
     * 编辑
     * @param req AddStoreLevelReq
     * @return req StoreLevel
     */
    public AddStoreLevelReq update(@RequestBody AddStoreLevelReq req) throws BaseException {
        log.info("StoreLevelManager update start, AddStoreLevelReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);
        String storeLevelId = req.getStoreLevelId();
        AssertUtil.isNotNull(storeLevelId, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_NULL);
        AssertUtil.isNotNull(req.getLevelName(), OfferBaseMessageCodeEnum.STORE_LEVEL_NAME_IS_NULL);

        // 名称唯一校验
        AddStoreLevelReq addStoreLevelReq = storeLevelMapper.queryByLevelName(req.getLevelName());
        if (null != addStoreLevelReq && !req.getModifyBy().equals(addStoreLevelReq.getModifyBy())) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_LEVEL_NAME_IS_EXIST);
        }
        StoreLevel storeLevel = storeLevelMapper.selectById(storeLevelId);
        AssertUtil.isNotNull(storeLevel, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_ERROR);
        storeLevel.setComments(req.getComments());
        storeLevel.setLevelName(req.getLevelName());
        Date date = DBDateUtil.getCurrentDBDateTime();
        storeLevel.setModifyTime(date);
        storeLevel.setModifyBy(req.getModifyBy());
        storeLevelMapper.updateById(storeLevel);

        log.info("StoreLevelManager update end");
        return req;
    }


    /**
     * 删除
     * @param req DeleteStoreLevelReq
     */
    public void delete(DeleteStoreLevelReq req) throws BaseException {
        log.info("StoreLevelManager delete start, DeleteShoppingCartReq = [{}]", req);

        // 参数校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_ERROR);

        List<String> storeLevelIds = req.getStoreLevelIds();
        if (CollectionUtils.isEmpty(storeLevelIds)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_NULL);
        }

        for (String storeLevelId : storeLevelIds) {
            AssertUtil.isNotNull(storeLevelId, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_NULL);
            StoreLevel storeLevel = storeLevelMapper.selectById(storeLevelId);
            AssertUtil.isNotNull(storeLevel, OfferBaseMessageCodeEnum.STORE_LEVEL_ID_IS_ERROR);
            int cnt = storeLevelMapper.countStoreByLevelId(storeLevelId);
            if (cnt > 0) {
                throw new BaseException(OfferBaseMessageCodeEnum.STORE_LEVEL_IS_HAVE_STORE);
            }
        }
        storeLevelMapper.updateState(storeLevelIds, req.getModifyBy());

        log.info("StoreLevelManager delete end");
    }

}
