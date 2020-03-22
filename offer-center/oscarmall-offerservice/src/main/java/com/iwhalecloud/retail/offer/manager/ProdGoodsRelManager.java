package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsRelTypeDef;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsRelReq;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsRel;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsRelMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月7日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsRelManager {
    
    @Autowired
    ProdGoodsRelMapper prodGoodsRelMapper;


    //降低复杂度
    private void releaseForGoodsRel(List<ProdGoodsRel> goodsRelList) {
        if (CollectionUtils.isNotEmpty(goodsRelList)) {
            prodGoodsRelMapper.createProdGoodsRel(goodsRelList);
        }
    }
    public int updateProdGoodsRel(ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelManager updateProdGoodsRel start, ProdGoodsRelReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        String aGoodsId = req.getAgoodsId();
        String relType = req.getRelType();
        List<String> zgoodsIds = req.getZgoodsIds();
        String userId = getUserId();
        if (relType.equals(ProdGoodsRelTypeDef.MUTEX.toString())) {
            // 删除
            if (CollectionUtils.isEmpty(zgoodsIds)) {
                delProdGoodsRelByAGoodsId(aGoodsId);
            }
            else {
                delProdGoodsRel(req);
            }

            List<ProdGoodsRel> goodsRelList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(zgoodsIds)) {
                for (String zprodGood : zgoodsIds) {
                    // 获取目标端和对应端的商品关系
                    ProdGoodsRel goodsRel = new ProdGoodsRel();
                    goodsRel.setRelId(String.valueOf(UidGeneator.getUID()));
                    goodsRel.setAGoodsId(aGoodsId);
                    goodsRel.setRelType(relType);
                    goodsRel.setZGoodsId(zprodGood);
                    goodsRel.setCreateBy(userId);
                    goodsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    goodsRel.setModifyBy(userId);
                    goodsRel.setState(CommonStateDef.ACTIVE);
                    goodsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());

                    goodsRelList.add(goodsRel);
                    // 目标端与对应端商品关系相反情况
                    ProdGoodsRel reverseGoodsRel = new ProdGoodsRel();
                    reverseGoodsRel.setRelId(String.valueOf(UidGeneator.getUID()));
                    reverseGoodsRel.setAGoodsId(zprodGood);
                    reverseGoodsRel.setRelType(relType);
                    reverseGoodsRel.setZGoodsId(aGoodsId);
                    reverseGoodsRel.setCreateBy(userId);
                    reverseGoodsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    reverseGoodsRel.setModifyBy(userId);
                    reverseGoodsRel.setState(CommonStateDef.ACTIVE);
                    reverseGoodsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());

                    goodsRelList.add(reverseGoodsRel);

                }
            }
            releaseForGoodsRel(goodsRelList);
        }
        else {
            prodGoodsRelMapper.delProdGoodsRelByAGoodsIdAndType(req.getAgoodsId(), relType, userId);
            List<ProdGoodsRel> goodsRelList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(req.getZgoodsIds())) {
                for (String zprodGood : req.getZgoodsIds()) {
                    // 获取目标端和对应端的商品关系
                    ProdGoodsRel goodsRel = new ProdGoodsRel();
                    goodsRel.setRelId(String.valueOf(UidGeneator.getUID()));
                    goodsRel.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                    goodsRel.setModifyBy(userId);
                    goodsRel.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                    goodsRel.setAGoodsId(aGoodsId);
                    goodsRel.setRelType(relType);
                    goodsRel.setZGoodsId(zprodGood);
                    goodsRel.setCreateBy(userId);
                    goodsRel.setState(CommonStateDef.ACTIVE);
                    goodsRelList.add(goodsRel);
                }
            }
            releaseForGoodsRel(goodsRelList);
        }

        log.info("ProdGoodsRelManager updateProdGoodsRel end");
        return 0;
}


    public int delProdGoodsRel(ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelManager delProdGoodsRel start, ProdGoodsRelReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getAgoodsId(), OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(req.getZgoodsIds(), OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(req.getRelType(), OfferBaseMessageCodeEnum.PARAM_ERROR);

        // 校验商品关系是否存在
        List<ProdGoodsRel> existGoodsRels = prodGoodsRelMapper.qryExistRel(req.getAgoodsId(), req.getRelType(), req.getZgoodsIds());
        if (CollectionUtils.isEmpty(existGoodsRels)) {
            return 0;
        }

        String modifyBy = getUserId();

        log.info("ProdGoodsRelManager delProdGoodsRel end");
        return prodGoodsRelMapper.delProdGoodsRel(existGoodsRels, modifyBy);
    }


    public List<ProdGoods> qryExcludeProdGoods(ProdGoodsRelReq req) throws BaseException {
        log.info("ProdGoodsRelManager qryExcludeProdGoods start, ProdGoodsRelReq = [{}]", req);

        String aGoodsId = req.getAgoodsId();
        String relType = req.getRelType();
        if (StringUtils.isEmpty(aGoodsId) || StringUtils.isEmpty(relType)) {
            throw new BaseException(OfferBaseMessageCodeEnum.PROD_GOODS_ID_NULL);
        }

        List<ProdGoods> result = prodGoodsRelMapper.qryExcludeProdGoods(req);

        if (null == result) {
            result = new ArrayList<>();
        }

        log.info("ProdGoodsRelManager qryExcludeProdGoods end");
        return result;
    }


    /**
     * 清除所有aGoodsId商品的关系
     * @param aGoodsId String
     */
    public void delProdGoodsRelByAGoodsId(String aGoodsId) throws BaseException {
        log.info("ProdGoodsRelManager delProdGoodsRelByAGoodsId start, aGoodsId = [{}]", aGoodsId);

        AssertUtil.isNotNull(aGoodsId, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);

        String modifyBy = getUserId();
        prodGoodsRelMapper.delProdGoodsRelByAGoodsId(aGoodsId, modifyBy);

        log.info("ProdGoodsRelManager delProdGoodsRelByAGoodsId end");
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

}
