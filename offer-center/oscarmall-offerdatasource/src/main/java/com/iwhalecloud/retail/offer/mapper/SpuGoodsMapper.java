package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.PageSpuGoods;
import com.iwhalecloud.retail.offer.dto.req.PageSpuRelGoodsReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuRelGoodsResp;
import com.iwhalecloud.retail.offer.dto.resp.SpuGoodsResp;
import com.iwhalecloud.retail.offer.entity.SpuGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date  2019/05/27
 */
@Mapper
public interface SpuGoodsMapper extends BaseMapper<SpuGoods> {

    /**
     * 关联商品（分页）
     * @param page Page<PageSpuRelGoodsResp>
     * @param req PageSpuRelGoodsReq
     * @return Page<PageSpuRelGoodsResp>
     */
    Page<PageSpuRelGoodsResp> pageSpuRelGoods(Page<PageSpuRelGoodsResp> page, @Param("req") PageSpuRelGoodsReq req);

    /**
     * SPU关联商品（分页）
     * @param page Page<PageSpuGoodsResp>
     * @param req PageSpuReq
     * @return Page<PageSpuGoodsResp>
     */
    Page<PageSpuGoodsResp> pageSpuGoods(Page<PageSpuGoodsResp> page, @Param("req") PageSpuGoods req);

    /**
     * 根据商品id查询关联SPU 只有一个
     * @param goodsId String
     * @return List<SpuGoodsResp>
     */
    @DisableTenantParam
    SpuGoodsResp qrySpuGoodsByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 根据SpuId查询关联Spu商品
     * @param spuId String
     * @return List<SpuGoodsResp>
     */
    @DisableTenantParam
    List<SpuGoodsResp> listSpuGoodsBySpuId(@Param("spuId") String spuId);

    /**
     * 根据spuId查询是否存在关联商品,考虑到效率,直接查有索引的商品id且limit 1
     * @param spuId String
     * @return int
     */
    String querySpuGoodsGoodsIdBySpuId(@Param("spuId") String spuId);

    /**
     * 根据spuId查询是否存在关联商品数量
     * @param spuId String
     * @return int
     */
    int countSpuGoodsBySpuId(@Param("spuId") String spuId);
}
