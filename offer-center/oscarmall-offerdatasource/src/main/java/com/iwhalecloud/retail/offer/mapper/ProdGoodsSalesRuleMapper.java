package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.ProdGoodsSalesRuleDTO;
import com.iwhalecloud.retail.offer.entity.ProdGoodsSalesRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author fanxiaofei
 * @date 2019/3/04
 */
@Mapper
public interface ProdGoodsSalesRuleMapper extends BaseMapper<ProdGoodsSalesRule> {


    /**
     * 查询所有商品销售规则
     * @return List<ProdGoodsSalesRule>
     */
    List<ProdGoodsSalesRuleDTO> listProdGoodsSalesRule();
}
