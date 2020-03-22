package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.AbstractProdGoodsStockWaveDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsStockWaveReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockQtyResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockWaveResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsStockWave;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsStockWaveMapper;
import com.iwhalecloud.retail.offer.mapper.UtilsMapper;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsStockWaveManager {


    @Autowired
    private ProdGoodsStockWaveMapper prodGoodsStockWaveMapper;

    @Autowired
    private ProdGoodsManager prodGoodsManager;

    @Autowired
    private UtilsMapper utilsMapper;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;


    /**
     * 新增 库存上下架
     * @param req ProdGoodsStockWaveReq
     * @return ProdGoodsStockWaveResp
     */
    public ProdGoodsStockWaveResp create(ProdGoodsStockWaveReq req) throws BaseException {
        log.info("ProdGoodsStockWaveManager create start, ProdGoodsStockWaveReq = [{}]", req);

        // 参数的校验
        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        checkStockInType(req);

        ProdGoodsStockWaveResp resp = prodGoodsStockWaveMapper.qrySameDateProdGoodsStockWave(req.getGoodsId(), req);
        if (null != resp) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_DATE_IS_ERROR);
        }

        // 校验波次时间是否在货架期内
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(req.getGoodsId(), null);
        if (req.getStockInDate().before(prodGoods.getMarketingBeginTime()) || req.getStockInDate().after(prodGoods.getMarketingEndTime())
                || req.getStockInDate().equals(prodGoods.getMarketingEndTime())) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_DATE_OUT_ERROR);
        }

        // 校验基础库存和波次库存总和与产品库存
        int sum = prodGoodsManager.checkGoodsQty(prodGoods, false);
        Long qtyLong = Long.valueOf(req.getQty().trim());
        Long sumLong = Long.valueOf(Integer.toString(sum));
        if (sumLong < qtyLong) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }
        ProdGoodsStockWave prodGoodsStockWave = new ProdGoodsStockWave();
        BeanUtils.copyProperties(req, prodGoodsStockWave);
        prodGoodsStockWave.setState(AbstractProdGoodsStockWaveDef.STATUS_A);
        prodGoodsStockWave.setStockWaveId(UidGeneator.getUIDStr());
        prodGoodsStockWave.setStateDate(utilsMapper.getCurrentDate());
        prodGoodsStockWave.setCreateTime(utilsMapper.getCurrentDate());
        prodGoodsStockWave.setModifyTime(utilsMapper.getCurrentDate());

        UserDTO tmpUserDTO = req.getUserDTO();
        if (null == tmpUserDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        }

        prodGoodsStockWave.setCreateBy(tmpUserDTO.getUserId().toString());
        prodGoodsStockWave.setModifyBy(tmpUserDTO.getUserId().toString());
        prodGoodsStockWaveMapper.insert(prodGoodsStockWave);
        ProdGoodsStockWaveResp result = new ProdGoodsStockWaveResp();
        BeanUtils.copyProperties(prodGoodsStockWave, result);

        log.info("ProdGoodsStockWaveManager create end");
        return result;
    }

    /**
     * 更新 库存上下架
     * @param prodGoodsStockWaveReqList List<ProdGoodsStockWaveReq>
     */
    public void updateStockWaveList(String goodsId, List<ProdGoodsStockWaveReq> prodGoodsStockWaveReqList) throws BaseException {
        log.info("ProdGoodsStockWaveManager create start, req = [{}]", prodGoodsStockWaveReqList);
        Long qtyLong = 0L;

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        int sum = prodGoodsManager.checkGoodsQty(prodGoods, false);
        Long sumLong = Long.valueOf(Integer.toString(sum));

        if (CollectionUtils.isNotEmpty(prodGoodsStockWaveReqList)) {
            for (ProdGoodsStockWaveReq req : prodGoodsStockWaveReqList) {
                // 校验波次时间是否在货架期内
                if (req.getStockInDate().before(prodGoods.getMarketingBeginTime()) || req.getStockInDate().after(prodGoods.getMarketingEndTime())
                    || req.getStockInDate().equals(prodGoods.getMarketingEndTime())) {
                    throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_DATE_OUT_ERROR);
                }

                qtyLong += Long.valueOf(req.getQty().trim());
            }
        }

        // 校验基础库存和波次库存总和与产品库存
        if (sumLong < qtyLong) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }

        // 先删除后新增
        Map<String, Object> qryMap = new HashMap<>();
        qryMap.put(ColumnNameDef.GOODS_ID, goodsId);
        qryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<ProdGoodsStockWave> prodGoodsStockWaves = prodGoodsStockWaveMapper.selectByMap(qryMap);
        if (CollectionUtils.isNotEmpty(prodGoodsStockWaves)) {
            prodGoodsStockWaves.forEach(prodGoodsStockWave -> {
                prodGoodsStockWave.setModifyTime(utilsMapper.getCurrentDate());
                prodGoodsStockWave.setState(AbstractProdGoodsStockWaveDef.STATUS_X);
                prodGoodsStockWave.setStateDate(utilsMapper.getCurrentDate());
                prodGoodsStockWave.setModifyBy(HttpSessionUtil.get().getUserId().toString());
                prodGoodsStockWaveMapper.updateById(prodGoodsStockWave);
            });
        }

        if (CollectionUtils.isNotEmpty(prodGoodsStockWaveReqList)) {
            for (ProdGoodsStockWaveReq req : prodGoodsStockWaveReqList) {
                ProdGoodsStockWave prodGoodsStockWave = new ProdGoodsStockWave();
                BeanUtils.copyProperties(req, prodGoodsStockWave);
                prodGoodsStockWave.setState(AbstractProdGoodsStockWaveDef.STATUS_A);
                prodGoodsStockWave.setStockWaveId(UidGeneator.getUIDStr());
                prodGoodsStockWave.setStateDate(utilsMapper.getCurrentDate());
                prodGoodsStockWave.setCreateTime(utilsMapper.getCurrentDate());
                prodGoodsStockWave.setModifyTime(utilsMapper.getCurrentDate());

                prodGoodsStockWave.setCreateBy(HttpSessionUtil.get().getUserId().toString());
                prodGoodsStockWave.setModifyBy(HttpSessionUtil.get().getUserId().toString());
                prodGoodsStockWaveMapper.insert(prodGoodsStockWave);
            }
        }
        log.info("ProdGoodsStockWaveManager create end");
    }


    /**
     * 删除
     * @param stockWaveId id
     */
    public void delete(String stockWaveId, Long userId) throws BaseException {
        log.info("ProdGoodsStockWaveManager delete start, stockWaveId = [{}]", stockWaveId);

        checkState(stockWaveId);
        prodGoodsStockWaveMapper.deleteProdGoodsStockWave(stockWaveId, userId.toString());

        log.info("ProdGoodsStockWaveManager delete end");
    }


    /**
     * 编辑
     * @param stockWaveId String
     * @param req ProdGoodsStockWaveReq
     */
    public void update(String stockWaveId, ProdGoodsStockWaveReq req) throws BaseException {
        log.info("ProdGoodsStockWaveManager update start, ProdGoodsStockWaveReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        checkStockInType(req);
        checkState(stockWaveId);
        ProdGoodsStockWaveResp resp = prodGoodsStockWaveMapper.qrySameDateProdGoodsStockWave(req.getGoodsId(), req);
        if (resp != null && !resp.getStockWaveId().equals(stockWaveId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_DATE_IS_ERROR);
        }
        // 校验波次时间是否在货架期内
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(req.getGoodsId(), null);
        if (req.getStockInDate().before(prodGoods.getMarketingBeginTime()) || req.getStockInDate().after(prodGoods.getMarketingEndTime())
                || req.getStockInDate().equals(prodGoods.getMarketingEndTime())) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_DATE_OUT_ERROR);
        }

        // 校验基础库存和波次库存总和与产品库存
        int sum = prodGoodsManager.checkGoodsQty(prodGoods, false);
        // 当前波次旧库存补充
        ProdGoodsStockWave prodGoodsStockWave = prodGoodsStockWaveMapper.selectById(stockWaveId);
        sum = sum - Integer.parseInt(prodGoodsStockWave.getQty());
        if (sum < Integer.parseInt(req.getQty())) {
            throw new BaseException(OfferBaseMessageCodeEnum.PRODUCT_STOCK_NOT_ENOUGH);
        }

        UserDTO tmpUserDTO = req.getUserDTO();
        if (null == tmpUserDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.USER_INFO_IS_NULL);
        }

        req.setModifyBy(tmpUserDTO.getUserId().toString());
        prodGoodsStockWaveMapper.editProdGoodsStockWave(stockWaveId, req);

        log.info("ProdGoodsStockWaveManager update end");
    }


    /**
     * 校验stockInType
     * @param req ProdGoodsStockWaveReq
     */
    private void checkStockInType(ProdGoodsStockWaveReq req) throws BaseException {
        String stockInType = req.getStockInType();
        if (!AbstractProdGoodsStockWaveDef.STOCK_IN_TYPE_C.equals(stockInType) &&
                !AbstractProdGoodsStockWaveDef.STOCK_IN_TYPE_P.equals(stockInType)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_IN_TYPE_IS_ERROR);
        }
    }

    /**
     * 校验状态
     * @param stockWaveId String
     */
    private void checkState(String stockWaveId) throws BaseException {
        if (StringUtils.isEmpty(stockWaveId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STOCK_WAVE_ID_IS_NULL);
        }
        ProdGoodsStockWave prodGoodsStockWave = prodGoodsStockWaveMapper.selectById(stockWaveId);
        AssertUtil.isNotNull(prodGoodsStockWave, OfferBaseMessageCodeEnum.STOCK_WAVE_ID_IS_ERROR);
        if (prodGoodsStockWave.getState().equals(AbstractProdGoodsStockWaveDef.STATUS_X) ||
                prodGoodsStockWave.getState().equals(AbstractProdGoodsStockWaveDef.STATUS_B)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STATE_IS_ERROR);
        }
    }


    /**
     * 根据商品id查询库存上下架
     * @param goodsId String
     * @return List<ProdGoodsStockWave>
     */
    public List<ProdGoodsStockWaveResp> getProdGoodsStockWaveByGoodsId(String goodsId) throws BaseException {
        log.info("ProdGoodsStockWaveManager getProdGoodsStockWaveByGoodsId start, goodsId = [{}]", goodsId);

        if (StringUtils.isEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        List<ProdGoodsStockWaveResp> result = prodGoodsStockWaveMapper.listProdGoodsStockWaveByProdGoodsId(goodsId);

        if (null == result) {
            result = new ArrayList<>();
        }

        log.info("ProdGoodsStockWaveManager getProdGoodsStockWaveByGoodsId end");
        return result;
    }


    public ProdGoodsStockQtyResp qryStockQty(String goodsId) throws BaseException {
        log.info("ProdGoodsStockWaveManager qryStockQty start, goodsId = [{}]", goodsId);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        ProdGoodsStockQtyResp resp = new ProdGoodsStockQtyResp();
        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }
        if (null != prodGoods.getStockQty() && prodGoods.getStockQty() > 0) {
            resp.setQty(prodGoods.getStockQty());
        }
        else {
            resp.setQty(0L);
        }

        log.info("ProdGoodsStockWaveManager qryStockQty end");
        return resp;
    }

}
