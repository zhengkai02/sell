package com.iwhalecloud.retail.offer.mapper;

import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.entity.ProdGoodsContent;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface ProdGoodsContentMapper extends BaseMapper<ProdGoodsContent> {

    ProdGoodsContent selectGoodsContentById(@Param("goodsContentId") String goodsContentId);

    @DisableTenantParam
    List<ProdGoodsContent> selectGoodsContentByGoodsId(@Param("request")ProdGoodsQueryContentReq request);

    /**
     * 20191104新增批量查询
     * @param goodsIds
     * @return
     */
    @DisableTenantParam
    List<ProdGoodsContent> selectGoodsContentByGoodsIds(@Param("goodsIds")List<String> goodsIds);

}
