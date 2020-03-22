package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsSalesConditionReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesConditionResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsSalesCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanxiaofei
 * @date 2019/03/03
 */
@Mapper
public interface ProdGoodsSalesConditionMapper extends BaseMapper<ProdGoodsSalesCondition> {

    /**
     * 根据商品包id查询所有为1(渠道)规则
     * 
     * @param goodsId String
     * @return List<ProdGoodsSalesCondition>
     */
    List<ProdGoodsSalesConditionResp> listProdGoodsSalesConditionByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 根据商品ID查询所有销售规则名称
     * @param goodsId String
     * @return String
     */
    List<String> listProdGoodsSalesConditionOneByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 新增商品适用规则
     * 
     * @param req ProdGoodsSalesConditionReq
     */
    void addProdGoodsSalesCondition(ProdGoodsSalesConditionReq req);

    /**
     * 删除商品适用规则
     * 
     * @param req ProdGoodsSalesConditionReq
     */
    void deleteProdGoodsSalesCondition(ProdGoodsSalesConditionReq req);

    /**
     * 根据商品id删除所有适用规则
     * 
     * @param goodsId 商品id
     * @param salesRuleId 规则标识
     */
    void deleteProdGoodsSalesConditionByGoodsIdAndSalesRuleId(@Param("goodsId") String goodsId,
        @Param("salesRuleId") Integer salesRuleId, @Param("modifyBy") String modifyBy);

}
