package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.CouponSpecNamesResp;
import com.iwhalecloud.retail.offer.dto.client.req.CouponSpecNamesReq;
import com.iwhalecloud.retail.offer.dto.client.resp.CouponIdAndSpecNameDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.RightsCouponQueryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.RightsGoodsQueryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.RightsListQueryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.RightsQueryResp;
import com.iwhalecloud.retail.offer.dto.req.RightsReq;
import com.iwhalecloud.retail.offer.dto.resp.RightsCouponResp;
import com.iwhalecloud.retail.offer.dto.resp.RightsGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.RightsListResp;
import com.iwhalecloud.retail.offer.dto.resp.RightsResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.service.CouponService;
import com.iwhalecloud.retail.offer.service.RightsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 权益
 * 
 * @author fanxiaofei
 * @date 2019-03-19
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RightsManager {

    @Autowired
    private RightsService rightsService;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private ICache redisCache;

    /**
     * 查询用户权益
     * 
     * @param req RightsReq
     * @return RightsListResp
     * @throws BaseException 异常
     */
    public RightsListResp queryRights(RightsReq req) throws BaseException {
        log.info("RightManager queryRights start, RightsReq = [{}]", req);
        // 亚信接口，查询权益
        ResultVO<RightsListQueryResp> rightsRespResultVO = rightsService.queryUserRights(req);
        ResultVOCheckUtil.checkResultVO(rightsRespResultVO);
        RightsListResp result = new RightsListResp();
        if (null == rightsRespResultVO.getData()) {
            return result;
        }
        List<RightsQueryResp> rightsQueryRespList = rightsRespResultVO.getData().getRightList();
        // 封装商品和优惠券规格集合
        if (CollectionUtils.isNotEmpty(rightsQueryRespList)) {
            List<RightsResp> rightsRespList = new ArrayList<>(rightsQueryRespList.size());
            for (RightsQueryResp rightsQueryResp : rightsQueryRespList) {
                // 封装返回值
                RightsResp rightsResp = new RightsResp();
                rightsResp.setRightsRelId(rightsQueryResp.getRightsRelId());
                rightsResp.setRightsCode(rightsQueryResp.getCode());
                rightsResp.setOfferId(rightsQueryResp.getOfferId());
                rightsResp.setRightName(rightsQueryResp.getName());
                rightsResp.setDescription(rightsQueryResp.getDescription());
                rightsResp.setValidityTime(rightsQueryResp.getValidityTime());
                rightsResp.setIsTake(rightsQueryResp.getIsTake());
                String relationGoodsId = rightsQueryResp.getRelationGoodsId();
                if (StringUtils.isNotEmpty(relationGoodsId)) {
                    rightsResp.setOfferId(relationGoodsId);
                    ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(relationGoodsId, "Y");
                    AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_OFFER_ID_IS_ERROR);
                    rightsResp.setOfferName(prodGoods.getName());
                }
                // 封装商品
                List<RightsGoodsQueryResp> goodsList = rightsQueryResp.getGoodsList();
                List<String> goodsIdList = new ArrayList<>(goodsList.size());
                goodsList.forEach(rightsGoodsResp -> goodsIdList.add(rightsGoodsResp.getGoodsId()));
                releaseForRightsGoods(rightsResp, goodsList, goodsIdList);
                // 封装优惠券规格
                List<RightsCouponQueryResp> couponList = rightsQueryResp.getCouponList();
                List<Long> couponSpecIdList = new ArrayList<>(couponList.size());
                couponList
                    .forEach(rightsGoodsResp -> couponSpecIdList.add(Long.valueOf(rightsGoodsResp.getCouponId())));
                if (CollectionUtils.isNotEmpty(couponSpecIdList)) {
                    List<RightsCouponResp> rightsCouponDTOS = new ArrayList<>(couponList.size());
                    CouponSpecNamesReq couponSpecNamesReq = new CouponSpecNamesReq();
                    couponSpecNamesReq.setCouponSpecIds(couponSpecIdList);
                    log.info("RightsManager couponService.qryCouponSpecNamesByCouponSpecIds  couponSpecNamesReq = [{}]",
                        couponSpecNamesReq);
                    ResultVO<CouponSpecNamesResp> respResultVO = couponService
                        .qryCouponSpecNamesByCouponSpecIds(couponSpecNamesReq);
                    ResultVOCheckUtil.checkResultVO(respResultVO);
                    log.info("RightsManager couponService.qryCouponSpecNamesByCouponSpecIds  data = [{}]",
                        respResultVO.getData());
                    couponList.forEach(couponResp -> {

                        RightsCouponResp rightsCouponDTO = new RightsCouponResp();
                        BeanUtils.copyProperties(couponResp, rightsCouponDTO);
                        rightsCouponDTO.setCouponSpecId(couponResp.getCouponId());
                        respResultVO.getData().getCouponIdAndSpecNameList().forEach(
                            couponIdAndSpecNameDTO -> releaseForRightsCoupon(rightsCouponDTO, couponIdAndSpecNameDTO));
                        rightsCouponDTOS.add(rightsCouponDTO);
                    });
                    rightsResp.setCoupons(rightsCouponDTOS);
                }
                rightsRespList.add(rightsResp);
            }
            result.setRightList(rightsRespList);
        }
        log.info("RightManager queryRights end");
        return result;
    }

    private void releaseForRightsCoupon(RightsCouponResp rightsCouponDTO,
        CouponIdAndSpecNameDTO couponIdAndSpecNameDTO) {
        if (rightsCouponDTO.getCouponSpecId().equals(couponIdAndSpecNameDTO.getCouponSpecId() + "")) {
            rightsCouponDTO.setComments(couponIdAndSpecNameDTO.getComments());
            rightsCouponDTO.setCouponSpecName(couponIdAndSpecNameDTO.getCouponSpecName());
            rightsCouponDTO.setCouponType(couponIdAndSpecNameDTO.getCouponType());
            String discount;
            if (couponIdAndSpecNameDTO.getDiscountRate() != null) {
                final float ten = 10;
                float discoutRate = ((float) couponIdAndSpecNameDTO.getDiscountRate() / ten);
                discount = String.valueOf(discoutRate);
            }
            else {
                discount = String.valueOf(couponIdAndSpecNameDTO.getDiscountAmount());
            }
            rightsCouponDTO.setDiscount(discount);
            log.info("RightsManager getCouponStockQty couponSpecId = [{}]", rightsCouponDTO.getCouponSpecId());
            rightsCouponDTO
                .setStockQty(redisCache.getNumber(CacheKeyDef.COUPON_QTY, rightsCouponDTO.getCouponSpecId()));
            log.info("RightsManager getCouponStockQty qty = [{}]", rightsCouponDTO.getStockQty());

        }
    }

    private void releaseForRightsGoods(RightsResp rightsResp, List<RightsGoodsQueryResp> goodsList,
        List<String> goodsIdList) {
        if (CollectionUtils.isNotEmpty(goodsIdList)) {
            List<RightsGoodsResp> rightsGoodsResps = new ArrayList<>(goodsIdList.size());
            List<RightsGoodsResp> rightsGoodsRespList = prodGoodsMapper.queryGoodsNameByGoodsId(goodsIdList);
            goodsList.forEach(rightsGoodsResp -> {
                RightsGoodsResp rightsGoods = new RightsGoodsResp();
                BeanUtils.copyProperties(rightsGoodsResp, rightsGoods);
                rightsGoods.setOfferId(rightsGoodsResp.getGoodsId());
                rightsGoodsRespList.forEach(rgs -> {
                    if (rightsGoods.getOfferId().equals(rgs.getOfferId())) {
                        rightsGoods.setOfferName(rgs.getOfferName());
                        rightsGoods.setThumbnail(rgs.getThumbnail());
                        rightsGoods.setSubhead(rgs.getSubhead());
                        rightsGoods.setStockQty(redisCache.getNumber(CacheKeyDef.GOODS_QTY, rgs.getSn()));
                    }
                });
                rightsGoodsResps.add(rightsGoods);
            });
            rightsResp.setOffers(rightsGoodsResps);
        }
    }
}
