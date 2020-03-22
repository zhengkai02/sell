package com.iwhalecloud.retail.offer.job;

import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品货架期到期自动下架,每日凌晨触发
 * @author fanxiaofei
 * @date 2019/07/25
 */
@Slf4j
@RestController
public class UpdateGoodsStateService {

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;


    @PostMapping("/offer/autoupdategoodsstate")
    @Transactional(rollbackFor = Exception.class)
    public void autoUpdateGoodsState() {
        log.info("UpdateGoodsStateService autoUpdateGoodsState start");

        List<String> prodGoodsIdList = prodGoodsMapper.qryListByStateCAndMarketingEndTime();
        if (CollectionUtils.isNotEmpty(prodGoodsIdList)) {
            log.info("UpdateGoodsStateService qryListByStateCAndMarketingEndTime prodGoodsIdList = [{}]", prodGoodsIdList);
            prodGoodsMapper.updateByStateCAndMarketingEndTime(prodGoodsIdList);
        }

        log.info("UpdateGoodsStateService autoUpdateGoodsState end");
    }
}
