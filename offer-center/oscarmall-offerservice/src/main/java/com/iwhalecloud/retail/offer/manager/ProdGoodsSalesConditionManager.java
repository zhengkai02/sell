package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.req.UpdateProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesRuleResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsSalesCondition;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesConditionMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品适用规则
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsSalesConditionManager {


    @Autowired
    private ProdGoodsSalesConditionMapper prodGoodsSalesConditionMapper;


    /**
     * 新增
     * @param req ProdGoodsSalesConditionReq
     */
    public void create(ProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionManager create start, ProdGoodsSalesConditionReq = [{}]", req);

        checkProdGoodsAttrValueReq(req);

        String userId = getUserId();
        req.setCondId(UidGeneator.getUIDStr());
        req.setModifyBy(userId);
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        req.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        req.setCreateBy(userId);
        req.setState(CommonStateDef.ACTIVE);

        prodGoodsSalesConditionMapper.addProdGoodsSalesCondition(req);

        log.info("ProdGoodsSalesConditionManager create end");
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw  new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }


    /**
     * 删除
     * @param req ProdGoodsSalesConditionReq
     */
    public void delete(ProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionManager delete start, ProdGoodsSalesConditionReq = [{}]", req);

        checkProdGoodsAttrValueReq(req);

        String userId = getUserId();
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        req.setModifyBy(userId);
        prodGoodsSalesConditionMapper.deleteProdGoodsSalesCondition(req);

        log.info("ProdGoodsSalesConditionManager delete end");
    }


    /**
     * 修改
     * @param goodsId 商品id
     * @param req ProdGoodsSalesConditionReq
     */
    public void update(String goodsId, UpdateProdGoodsSalesConditionReq req) throws BaseException {
        log.info("ProdGoodsSalesConditionManager update start, goodsId = [{}], UpdateProdGoodsSalesConditionReq = [{}]", goodsId, req);

        Integer salesRuleId = req.getSalesRuleId();
        if (null == salesRuleId) {
            throw new BaseException(OfferBaseMessageCodeEnum.SALES_RULE_ID_IS_NULL);
        }
        List<String> objIds = req.getObjIds();

        // 删除商品id下所有适用规则
        String userId = getUserId();
        prodGoodsSalesConditionMapper.deleteProdGoodsSalesConditionByGoodsIdAndSalesRuleId(goodsId, salesRuleId, userId);
        // 新增
        if (CollectionUtils.isNotEmpty(objIds)) {
            objIds.forEach(objId -> {
                ProdGoodsSalesCondition prodGoodsSalesCondition = new ProdGoodsSalesCondition();
                prodGoodsSalesCondition.setCondId(UidGeneator.getUIDStr());
                prodGoodsSalesCondition.setGoodsId(goodsId);
                prodGoodsSalesCondition.setSalesRuleId(salesRuleId);
                prodGoodsSalesCondition.setObjId(objId);
                prodGoodsSalesCondition.setState(CommonStateDef.ACTIVE);
                prodGoodsSalesCondition.setCreateBy(userId);
                prodGoodsSalesCondition.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsSalesCondition.setModifyBy(userId);
                prodGoodsSalesCondition.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsSalesConditionMapper.insert(prodGoodsSalesCondition);
            });
        }

        log.info("ProdGoodsSalesConditionManager update end");
    }


    /**
     * 校验入参
     * @param req ProdGoodsSalesConditionReq
     */
    private void checkProdGoodsAttrValueReq(ProdGoodsSalesConditionReq req) throws BaseException {
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getSalesRuleId(), OfferBaseMessageCodeEnum.SALES_RULE_ID_IS_NULL);
        if (StringUtils.isEmpty(req.getGoodsId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        if (StringUtils.isEmpty(req.getObjId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.OBJ_ID_IS_NULL);
        }
    }


    /**
     * 根据商品id查询
     * @param goodsId 商品id
     */
    public List<ProdGoodsSalesRuleResp> listProdGoodsSalesConditionByProdGoodsId(String goodsId) throws BaseException {
        log.info("ProdGoodsSalesConditionManager listProdGoodsSalesConditionByProdGoodsId start, goodsId = [{}]", goodsId);

        // 校验参数
        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        List<ProdGoodsSalesConditionResp> prodGoodsSalesConditions = prodGoodsSalesConditionMapper.listProdGoodsSalesConditionByGoodsId(goodsId);
        // 构造返回值
        List<ProdGoodsSalesRuleResp> result = new ArrayList<>();
        prodGoodsSalesConditions.forEach(prodGoodsSalesConditionResp -> {
            Boolean flag = true;
            for (ProdGoodsSalesRuleResp target : result) {
                if (target.getSalesRuleId().equals(prodGoodsSalesConditionResp.getSalesRuleId())) {
                    flag = false;
                    target.getObjIdList().add(prodGoodsSalesConditionResp.getObjId());
                    break;
                }
            }
            if (flag) {
                ProdGoodsSalesRuleResp target = new ProdGoodsSalesRuleResp();
                target.setName(prodGoodsSalesConditionResp.getName());
                target.setComments(prodGoodsSalesConditionResp.getComments());
                target.setSalesRuleId(prodGoodsSalesConditionResp.getSalesRuleId());
                List<String> objIdList = new ArrayList<>();
                objIdList.add(prodGoodsSalesConditionResp.getObjId());
                target.setObjIdList(objIdList);
                result.add(target);
            }
        });

        log.info("ProdGoodsSalesConditionManager listProdGoodsSalesConditionByProdGoodsId end");
        return result;
    }


}
