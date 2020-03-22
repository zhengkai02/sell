package com.iwhalecloud.retail.offer.job;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * redis库存量同步到商品表的stock_qty，5分钟触发一次，此job无法确保redis数据及时更新至数据库
 * @author fanxiaofei
 * @date 2019/04/12
 */
@Slf4j
@RestController
public class SaveStockQtyService {

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private ICache redisCache;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/offer/autosaveqty")
    public void autoStockQtyByRedis() {
        log.info("SaveStockQtyService autoStockQtyByRedis start");

        List<ProdGoods> prodGoodsList = prodGoodsMapper.qryList4SynGoodsQty();
        if (CollectionUtils.isNotEmpty(prodGoodsList)) {
            log.info("SaveStockQtyService autoStockQtyByRedis prodGoodsList = [{}]", prodGoodsList);
            prodGoodsList.forEach(prodGoods -> {
                // redis里库存
                String redisQty = redisCache.getNumber(CacheKeyDef.GOODS_QTY, prodGoods.getSn());
                log.info("SaveStockQtyService autoStockQtyByRedis redisQty = [{}]", redisQty);
              if (StringUtils.isNotEmpty(redisQty)) {
                  Long qty = Long.valueOf(redisQty);
                  // 此时redis库存和数据库不相同，才更新数据库
                  if (!qty.equals(prodGoods.getStockQty())) {
                      prodGoods.setStockQty(qty);
                      prodGoodsMapper.updateById(prodGoods);
                  }
                }
            });
        }

        log.info("SaveStockQtyService autoStockQtyByRedis end");
    }
}
