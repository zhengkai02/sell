package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.PageSpuReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuResp;
import com.iwhalecloud.retail.offer.entity.Spu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author fanxiaofei
 * @date  2019/05/24
 */
@Mapper
public interface SpuMapper extends BaseMapper<Spu> {


    /**
     * SPU列表查询（分页）
     * @param page Page<PageSpuResp>
     * @param req PageSpuReq
     * @return Page<PageSpuResp>
     */
    Page<PageSpuResp> pageSpu(Page<PageSpuResp> page, @Param("req") PageSpuReq req);

    /**
     * 根据spuId更新SkuAttrIds属性
     * @param spuId String
     */
    void updateSkuAttrIdsBySpuId(@Param("spuId") String spuId, @Param("modifyBy") String modifyBy);

}
