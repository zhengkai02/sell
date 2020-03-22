package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.AddStoreLevelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreLevelReq;
import com.iwhalecloud.retail.offer.entity.StoreLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date  2019/04/28
 */
@Mapper
public interface StoreLevelMapper extends BaseMapper<StoreLevel> {

    /**
     * 分页查询店铺级别
     * @param page Page<AddStoreLevelReq>
     * @param req QueryStoreLevelReq
     * @return Page<AddStoreLevelReq>
     */
    Page<AddStoreLevelReq> queryStoreLevelPage(Page<AddStoreLevelReq> page, @Param("req") QueryStoreLevelReq req);

    /**
     * 批量更新状态
     * @param storeLevelIds List<String>
     * @param modifyBy Integer
     */
    void updateState(@Param("storeLevelIds") List<String> storeLevelIds, @Param("modifyBY") String modifyBy);

    /**
     * 根据名称查询店铺级别
     * @param levelName 名称
     * @return StoreLevel
     */
    AddStoreLevelReq queryByLevelName(String levelName);

    /**
     * 根据等级id查询关联店铺数,店铺等级非X
     * @param storeLevelId String
     * @return int
     */
    int countStoreByLevelId(String storeLevelId);
}
