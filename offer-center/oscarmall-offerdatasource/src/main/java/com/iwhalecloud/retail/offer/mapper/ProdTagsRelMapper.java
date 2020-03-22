package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.entity.ProdTagsRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date 2019/03/04
 */
@Mapper
public interface ProdTagsRelMapper extends BaseMapper<ProdTagsRel> {

    /**
     * 商品下所有标签id
     * @param goodsId String
     * @return List<Long>
     */
    @DisableTenantParam
    List<String> listTagIdByProdGoodsId(@Param("goodsId") String goodsId);

    /**
     * 标签下所有商品id
     * @param tagId String
     * @return List<String>
     */
    @DisableTenantParam
    List<String> listProdGoodsIdByTagId(@Param("tagId") String tagId);

    /**
     * 删除商品的所有标签
     * @param goodsId
     */
    void deleteByGoodsId(@Param("goodsId") String goodsId, @Param("modifyBy") String modifyBy);

}
