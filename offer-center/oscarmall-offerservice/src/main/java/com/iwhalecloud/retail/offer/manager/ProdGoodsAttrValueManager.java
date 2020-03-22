package com.iwhalecloud.retail.offer.manager;

import java.util.List;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractAttrDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsAttrValueReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrValueResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.AttrValue;
import com.iwhalecloud.retail.offer.entity.ProdGoodsAttrValue;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.AttrValueMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsAttrValueMapper;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsAttrValueManager {


    @Autowired
    private ProdGoodsAttrValueMapper prodGoodsAttrValueMapper;

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private CommonManager commonManager;

    /**
     * 新增 商品属性
     * @param list List<ProdGoodsAttrValueReq>
     */
    public void create(List<ProdGoodsAttrValueReq> list) throws BaseException {
        log.info("ProdGoodsAttrValueManager create start, List<ProdGoodsAttrValueReq> = [{}]", list);

        for (ProdGoodsAttrValueReq req : list) {
            checkProdGoodsAttrValueReq(req);
            // 清空缓存
            commonManager.cacheClear(req.getGoodsId());
        }

        String userId = getUserId();

        list.forEach(req -> {
            ProdGoodsAttrValue prodGoodsAttrValue = new ProdGoodsAttrValue();
            prodGoodsAttrValue.setGoodsAttrId(UidGeneator.getUID());
            prodGoodsAttrValue.setAttrId(req.getAttrId());
            prodGoodsAttrValue.setGoodsId(req.getGoodsId());
            prodGoodsAttrValue.setAttrValue(req.getAttrValue());
            prodGoodsAttrValue.setTenantId(req.getTenantId());
            prodGoodsAttrValue.setPriority(req.getPriority());
            prodGoodsAttrValue.setModifyBy(userId);
            prodGoodsAttrValue.setModifyTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsAttrValue.setCreateBy(userId);
            prodGoodsAttrValue.setCreateTime(DBDateUtil.getCurrentDBDateTime());
            prodGoodsAttrValue.setState(CommonStateDef.ACTIVE);
            prodGoodsAttrValueMapper.insert(prodGoodsAttrValue);
        });

        log.info("ProdGoodsAttrValueManager create end");
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    /**
     * 删除
     * @param goodsId 商品id
     * @param attrId 属性id
     */
    public void delete(String goodsId, String attrId) throws BaseException {
        log.info("ProdGoodsAttrValueManager delete start, goodsId = [{}], attrId = [{}]", goodsId, attrId);

        // 参数的校验
        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        if (StringUtils.isEmpty(attrId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_ID_IS_NULL);
        }
        String modifyBy = getUserId();
        prodGoodsAttrValueMapper.deleteProdGoodsAttrValue(goodsId, attrId, modifyBy);

        // 清空缓存
        commonManager.cacheClear(goodsId);

        log.info("ProdGoodsAttrValueManager delete end");
    }


    /**
     * 编辑
     * @param goodsId String
     * @param attrId String
     * @param req ProdGoodsStockWaveReq
     */
    public void update(String goodsId, String attrId, ProdGoodsAttrValueReq req) throws BaseException {
        log.info("ProdGoodsAttrValueManager update start, goodsId = [{}], attrId = [{}], ProdGoodsAttrValueReq = [{}]", goodsId, attrId, req);

        // 参数的校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(goodsId, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotNull(attrId, OfferBaseMessageCodeEnum.ATTR_ID_IS_NULL);
        Attr attr = attrMapper.selectById(req.getAttrId());
        AssertUtil.isNotNull(attr, OfferBaseMessageCodeEnum.ATTR_ID_IS_ERROR);
        if (AbstractAttrDef.NULLABLE_N.equals(attr.getNullable()) && StringUtils.isEmpty(req.getAttrValue())) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_VALUE_IS_NULL);
        }
        String modifyBy = getUserId();
        req.setAttrId(attrId);
        req.setGoodsId(goodsId);
        req.setModifyBy(modifyBy);
        prodGoodsAttrValueMapper.editProdGoodsAttrValue(req);

        // 清空缓存
        commonManager.cacheClear(goodsId);

        log.info("ProdGoodsAttrValueManager update end");
    }


    /**
     * 校验ProdGoodsAttrValueReq入参
     * @param req ProdGoodsAttrValueReq
     */
    private void checkProdGoodsAttrValueReq(ProdGoodsAttrValueReq req) throws BaseException {
        if (StringUtils.isEmpty(req.getGoodsId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        if (StringUtils.isEmpty(req.getAttrId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_ID_IS_NULL);
        }
        Attr attr = attrMapper.selectById(req.getAttrId());
        AssertUtil.isNotNull(attr, OfferBaseMessageCodeEnum.ATTR_ID_IS_ERROR);
        if (attr.getNullable().equals(AbstractAttrDef.NULLABLE_N) && StringUtils.isEmpty(req.getAttrValue())) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_VALUE_IS_NULL);
        }
    }


    /**
     * 根据商品id查询属性值
     * @param goodsId String
     * @return List<AttrValueResp>
     */
    public List<AttrValueResp> getProdGoodsAttrValueByGoodsId(String goodsId) throws BaseException {
        log.info("ProdGoodsAttrValueManager getProdGoodsAttrValueByGoodsId start, goodsId = [{}]", goodsId);

        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        // 商品属性值
        List<AttrValueResp> result = prodGoodsAttrValueMapper.listAttrValueRespByProdGoodsId(goodsId);
        result.forEach(attrValueResp -> {
            List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(attrValueResp.getAttrId());
            attrValueResp.setAttrValueList(attrValues);
            if (attrValueResp.getInputType() == AbstractAttrDef.INPUT_TYPE_TEXT || attrValueResp.getInputType() == AbstractAttrDef.INPUT_TYPE_CUSTOM) {
                attrValueResp.setValueMark(attrValueResp.getAttrValue());
            }
            if (attrValueResp.getInputType() == AbstractAttrDef.INPUT_TYPE_MULTI_SELECT && StringUtils.isNotEmpty(attrValueResp.getAttrValue())) {
                String[] values = attrValueResp.getAttrValue().split(",");
                StringBuilder bld = new StringBuilder();
                for (String value : values) {
                    attrValues.forEach(attrValue -> {
                        if (value.equals(attrValue.getValue())) {
                            bld.append(attrValue.getValueMark()).append(",");
                        }
                    });
                }

                attrValueResp.setValueMark(bld.toString().substring(0, bld.length() - 1));
            }
        });

        log.info("ProdGoodsAttrValueManager getProdGoodsAttrValueByGoodsId end");
        return result;
    }

}
