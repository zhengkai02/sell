package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreRuleInstResp;
import com.iwhalecloud.retail.offer.entity.StoreRuleInst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date  2019/04/28
 */
@Mapper
public interface StoreRuleInstMapper extends BaseMapper<StoreRuleInst> {


    /**
     * 根据店铺id查店铺适用规则实例 渠道为1
     * @param storeId String
     * @return QueryStoreRuleInstResp
     */
    List<QueryStoreRuleInstResp> listStoreRuleInstByStoreId(String storeId);

    /**
     * 根据店铺id查询所有规则实例的id 渠道为1
     * @param storeId String
     * @return List<String>
     */
    List<String> queryIdByStoreId(String storeId);

    void deleteStoreRuleInstByIds(@Param("ruleInstIds") List<String> ruleInstIds, @Param("modifyBy") String modifyBy);
}
