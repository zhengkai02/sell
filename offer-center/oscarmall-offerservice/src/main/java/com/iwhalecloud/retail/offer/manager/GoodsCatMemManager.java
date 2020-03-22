package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatMemListReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.entity.ContactChannel;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatMem;
import com.iwhalecloud.retail.offer.mapper.ContactChannelMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsCatMemMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesConditionMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/1 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GoodsCatMemManager {

    @Autowired
    private ProdGoodsCatMemMapper prodGoodsCatMemMapper;

    @Autowired
    private ProdGoodsSalesConditionMapper prodGoodsSalesConditionMapper;

    @Autowired
    private ContactChannelMapper contactChannelMapper;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    private void releaseForMemList(QryGoodsCatMemListReq req) throws BaseException {
        if (null == req || null == req.getPageNo() || null == req.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(req.getCatId()) && StringUtils.isEmpty(req.getCatType())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }
    }

    public Page<QryGoodsCatMemListResp> qryGoodsCatMemList(QryGoodsCatMemListReq req) throws BaseException {
        log.info("GoodsCatMemManager qryGoodsCatMemList start, QryGoodsCatMemListReq = [{}]", req);
        releaseForMemList(req);
        Page<QryGoodsCatMemListResp> page = new Page<>(req.getPageNo(), req.getPageSize());

        // 补充销售渠道
        Page<QryGoodsCatMemListResp> result = prodGoodsCatMemMapper.qryGoodsCatMemList(page, req);
        log.info("GoodsCatMemManager qryGoodsCatMemList qryGoodsCatMemList result = [{}]", result);

        List<QryGoodsCatMemListResp> list = result.getRecords();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(goodsMem -> {
                String saleChannel = "";
                StringBuilder bld = new StringBuilder();
                bld.append(saleChannel);
                List<ProdGoodsSalesConditionResp> prodGoodsSalesConditions = prodGoodsSalesConditionMapper.listProdGoodsSalesConditionByGoodsId(goodsMem.getGoodsId());
                if (CollectionUtils.isNotEmpty(prodGoodsSalesConditions)) {
                    for (ProdGoodsSalesConditionResp prodGoodsSalesConditionResp : prodGoodsSalesConditions) {
                        if (prodGoodsSalesConditionResp.getSalesRuleId() == 1) {
                            ContactChannel contactChannel = contactChannelMapper.selectById(prodGoodsSalesConditionResp.getObjId());
                            if (null != contactChannel) {
                                bld.append(contactChannel.getContactChannelName());
                                bld.append(",");
                            }
                        }
                    }
                    saleChannel = bld.toString();
                    if (StringUtils.isNotEmpty(saleChannel)) {
                        goodsMem.setSaleChannel(saleChannel.substring(0, saleChannel.length() - 1));
                    }
                    else {
                        goodsMem.setSaleChannel("");
                    }
                }
                else {
                    goodsMem.setSaleChannel(saleChannel);
                }
            });
        }

        log.info("GoodsCatMemManager qryGoodsCatMemList end");
        return result;
    }

    public List<QryGoodsCatMemListResp> selectProdGoodsCatMemByCat(String catId) throws BaseException {
        log.info("GoodsCatMemManager selectProdGoodsCatMemByCat start, catId = [{}]", catId);
        log.info("GoodsCatMemManager selectProdGoodsCatMemByCat end");
        return prodGoodsCatMemMapper.qryProdGoodsCatMemDetailByCatId(catId);
    }


    public int batchDeleteGoodsMem(List<String> offerIds) throws BaseException {
        log.info("GoodsCatMemManager batchDeleteGoodsMem start, List<String> = [{}]", offerIds);

        if (CollectionUtils.isEmpty(offerIds)) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_MEMS_EMPTY);
        }

        if (prodGoodsCatMemMapper.qryPublishMem(offerIds) > 0) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAT_MEMS_HAS_PUBLISHED_GOODS);
        }

        log.info("GoodsCatMemManager batchDeleteGoodsMem end");
        return prodGoodsCatMemMapper.batchDeleteGoodsMem(offerIds, getUserId());
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    public void updateGoodsMem(String catId, List<String> offerIds) throws BaseException {
        log.info("GoodsCatMemManager updateGoodsMem start, catId = [{}], offerIds = [{}]", catId, offerIds);

        // 并集
        List<String> deleteGoodsId = prodGoodsCatMemMapper.qryGoodsIdByCatId(catId);
        log.info("GoodsCatMemManager updateGoodsMem deleteGoodsId = [{}]", deleteGoodsId);
        if (CollectionUtils.isNotEmpty(deleteGoodsId)) {
            deleteGoodsId.removeAll(offerIds);
            deleteGoodsId.addAll(offerIds);
        }
        log.info("GoodsCatMemManager updateGoodsMem addAll = [{}]", deleteGoodsId);

        prodGoodsCatMemMapper.deleteCatMemById(catId, getUserId());

        String createBy = getUserId();
        if (CollectionUtils.isNotEmpty(offerIds)) {
            offerIds.forEach(offerId -> {
                ProdGoodsCatMem prodGoodsCatMem = new ProdGoodsCatMem();
                prodGoodsCatMem.setGoodsId(offerId);
                prodGoodsCatMem.setCatMemId(UidGeneator.getUIDStr());
                prodGoodsCatMem.setCatId(catId);
                prodGoodsCatMem.setCreateBy(createBy);
                prodGoodsCatMem.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsCatMem.setModifyBy(createBy);
                prodGoodsCatMem.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsCatMem.setState(CommonStateDef.ACTIVE);
                prodGoodsCatMemMapper.insert(prodGoodsCatMem);
            });
        }
        // 更新商品时间触发ES
        deleteGoodsId.forEach(goodsId -> {
            prodGoodsMapper.updateProGoodsByES(goodsId);
        });

        log.info("GoodsCatMemManager updateGoodsMem end");
    }
}
