package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.dto.client.req.QueryGoodsPriceReq;
import com.iwhalecloud.retail.offer.entity.GoodsPrice;
import com.iwhalecloud.retail.offer.entity.PriceFactor;
import com.iwhalecloud.retail.offer.mapper.GoodsPriceFactorMapper;
import com.iwhalecloud.retail.offer.mapper.GoodsPriceMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huminghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/8 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GoodsPriceManager {

    @Autowired
    private GoodsPriceFactorMapper goodsPriceFactorMapper;

    @Autowired
    private GoodsPriceMapper goodsPriceMapper;

    public List<PriceFactor> qryGoodsPriceFactorList() {
        log.info("GoodsPriceManager qryGoodsPriceFactorList start");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<PriceFactor> priceFactors = goodsPriceFactorMapper.selectList(queryWrapper);
        log.info("GoodsPriceManager qryGoodsPriceFactorList end");
        return priceFactors;
    }

    public List<GoodsPrice> qryGoodsPriceList(QueryGoodsPriceReq req) {
        log.info("GoodsPriceManager qryGoodsPriceList start");
        List<GoodsPrice> goodsPrices = goodsPriceMapper.qryGoodsPriceList(req);
        log.info("GoodsPriceManager qryGoodsPriceList end");
        return goodsPrices;
    }

    public GoodsPrice addGoodsPrice(GoodsPrice req) {
        log.info("GoodsPriceManager addGoodsPrice start");
        req.setGoodsPriceId(UidGeneator.getUIDStr());
        req.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        req.setCreateBy(HttpSessionUtil.get().getUserId().toString());
        req.setModifyBy(HttpSessionUtil.get().getUserId().toString());
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        req.setState(CommonStateDef.ACTIVE);
        req.setStateDate(DBDateUtil.getCurrentDBDateTime());
        goodsPriceMapper.insert(req);

        log.info("GoodsPriceManager addGoodsPrice end");
        return req;
    }

    public GoodsPrice modGoodsPrice(String goodsPriceId, GoodsPrice req) {
        log.info("GoodsPriceManager modGoodsPrice start");
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        req.setModifyBy(HttpSessionUtil.get().getUserId().toString());
        GoodsPrice goodsPrice = goodsPriceMapper.selectById(goodsPriceId);
        BeanUtils.copyProperties(req, goodsPrice);
        goodsPriceMapper.updateById(goodsPrice);

        log.info("GoodsPriceManager modGoodsPrice end");
        return req;
    }

    public void delGoodsPrice(String goodsPriceId) {
        log.info("GoodsPriceManager delGoodsPrice start");
        GoodsPrice goodsPrice = goodsPriceMapper.selectById(goodsPriceId);
        goodsPrice.setState(CommonStateDef.INACTIVE);
        goodsPrice.setStateDate(DBDateUtil.getCurrentDBDateTime());
        goodsPrice.setModifyBy(HttpSessionUtil.get().getUserId().toString());
        goodsPrice.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        goodsPriceMapper.updateById(goodsPrice);

        log.info("GoodsPriceManager delGoodsPrice end");
    }
}
