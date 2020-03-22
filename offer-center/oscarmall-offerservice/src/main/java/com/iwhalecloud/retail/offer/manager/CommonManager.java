package com.iwhalecloud.retail.offer.manager;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.cache.ICache;
import com.iwhalecloud.retail.common.consts.CacheKeyDef;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.DomainReq;
import com.iwhalecloud.retail.offer.entity.Domain;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.DomainMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommonManager {
    
    @Autowired
    private DomainMapper domainMapper;

    @Resource
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private ICache redisCache;

    
    public List<Domain> qryDomainInfo(DomainReq req) {
        log.info("CommonManager qryDomainInfo start req = [{}]", req);
        Domain request = new Domain();
        request.setTableName(req.getTableName());
        request.setColumnName(req.getColumnName());
        
        List<Domain> resp = domainMapper.select(request);
        log.info("CommonManager qryDomainInfo end");
        return resp;
    }

    /**
     * @Author wangzhongbao
     * @Description 清除缓存
     * @Date 15:06 2019/4/25
     * @Param [goodsId]
     * @return void
     **/
    public void cacheClear(String goodsId) throws BaseException {
        log.info("CommonManager cacheClear start req = [{}]", goodsId);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, "Y");
        if (null != prodGoods) {
            // 清空缓存
            redisCache.delete(CacheKeyDef.GOODS_DEAIL_ID_CACHE, prodGoods.getGoodsId());
            redisCache.delete(CacheKeyDef.GOODS_DEAIL_CODE_CACHE, prodGoods.getSn());
        }

        log.info("CommonManager cacheClear end");
    }

}
