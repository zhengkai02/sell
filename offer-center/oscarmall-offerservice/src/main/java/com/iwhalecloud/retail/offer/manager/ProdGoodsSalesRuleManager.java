package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.offer.dto.ProdGoodsSalesRuleDTO;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsSalesRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 商品销售规则
 * @author fanxiaofei
 * @date 2019/03/05
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsSalesRuleManager {

    @Autowired
    private ProdGoodsSalesRuleMapper prodGoodsSalesRuleMapper;


    /**
     * 查询商品销售规则
     * @return List<ProdGoodsSalesRule>
     */
    public List<ProdGoodsSalesRuleDTO> listProdGoodsSalesRule() {
        log.info("ProdGoodsSalesRuleManager listProdGoodsSalesRule start");
        List<ProdGoodsSalesRuleDTO> result = prodGoodsSalesRuleMapper.listProdGoodsSalesRule();
        log.info("ProdGoodsSalesRuleManager listProdGoodsSalesRule end");
        return result;
    }

}
