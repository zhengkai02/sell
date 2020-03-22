package com.iwhalecloud.retail.offer.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.ProdGoodsStockWaveStateDef;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsStockWave;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsStockWaveMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anonymous on 2019/3/28. copy AutoGroundJobService 由于要的很急 有些问题忽略了.... ZMP 1547538
 */
@RestController
@Slf4j
public class AutoGroundService {

    @Autowired
    private ProdGoodsStockWaveMapper prodGoodsStockWaveMapper;

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private ICache redisCache;

    /**
     * 扫描PROD_GOODS_STOCK_WAVE表中状态为A，并且已过STOCK_IN_DATE的数据进行自动库存更新，
     * 根据STOCK_IN_TYPE来决定是覆盖方式还是追加方式，上架过程中需要将状态置为B(防止重复上架)，上架完成之后置为C
     */
    @Transactional
    @PostMapping("/offer/auto_shelf")
    public void autoGoodsStock() {
        log.info("AutoGroundService autoGoodsStock start");

        Date now = DBDateUtil.getCurrentDBDateTime();

        // 目前数量不会太多 暂时简单处理
        List<ProdGoodsStockWave> prodGoodsStockWaveList = prodGoodsStockWaveMapper
            .selectList(new QueryWrapper<ProdGoodsStockWave>()
                .eq(ColumnNameDef.STATE, ProdGoodsStockWaveStateDef.CREATED).le(ColumnNameDef.STOCK_IN_DATE, now));

        if (!CollectionUtils.isEmpty(prodGoodsStockWaveList)) {
            // 更新状态为B 上架过程中
            updateStockingState(prodGoodsStockWaveList, now);

            prodGoodsStockWaveList.forEach(stock -> dealOneGoodsStockWave(stock, now));

        }
        log.info("AutoGroundService autoGoodsStock end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStockingState(List<ProdGoodsStockWave> prodGoodsStockWaveList, Date now) {
        List<String> waveIds = new ArrayList<>(prodGoodsStockWaveList.size());
        prodGoodsStockWaveList.forEach(wave -> waveIds.add(wave.getStockWaveId()));
        prodGoodsStockWaveMapper.update(new ProdGoodsStockWave(),
            new UpdateWrapper<ProdGoodsStockWave>().set(ColumnNameDef.STATE, ProdGoodsStockWaveStateDef.STOCKING)
                .set(ColumnNameDef.STATE_DATE, now).in(ColumnNameDef.STOCK_WAVE_ID, waveIds));
    }

    /**
     * @param goodsStockWave ProdGoodsStockWave
     * @param now Date
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void dealOneGoodsStockWave(ProdGoodsStockWave goodsStockWave, Date now) {
        if (StringUtils.isEmpty(goodsStockWave.getGoodsId())) {
            return;
        }
        ProdGoods prodGoods = prodGoodsMapper.selectById(goodsStockWave.getGoodsId());
        if (prodGoods == null) {
            return;
        }
        // 覆盖
        if (CommonStateDef.STOCK_IN_TYPE_C.equals(goodsStockWave.getStockInType())) {
            redisCache.setNumber(CacheKeyDef.GOODS_QTY, prodGoods.getSn(), goodsStockWave.getQty());
        }

        // 追加
        else if (CommonStateDef.STOCK_IN_TYPE_P.equals(goodsStockWave.getStockInType())) {
            redisCache.incrBy(CacheKeyDef.GOODS_QTY, prodGoods.getSn(), Integer.valueOf(goodsStockWave.getQty()));
        }

        // 更新状态为C 上架完成
        prodGoodsStockWaveMapper.update(new ProdGoodsStockWave(),
            new UpdateWrapper<ProdGoodsStockWave>().set(ColumnNameDef.STATE, ProdGoodsStockWaveStateDef.STOCKED)
                .set(ColumnNameDef.STATE_DATE, now).eq(ColumnNameDef.STOCK_WAVE_ID, goodsStockWave.getStockWaveId()));
    }

}
