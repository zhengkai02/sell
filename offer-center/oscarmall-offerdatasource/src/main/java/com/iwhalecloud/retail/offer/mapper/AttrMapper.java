package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.QryAttrReq;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.QueryAttrListReq;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanxiaofei
 * @date 2019/03/04
 */
@Mapper
public interface AttrMapper extends BaseMapper<Attr> {

    /**
     * 查询所有属性
     * @param page
     * @param request
     * @return
     */
    Page<Attr> queryAttrList(Page<Attr> page, @Param("request") QryAttrReq request);

    /**
     * 根据商品id过滤当前商品已有属性的其他所有属性
     * @param goodsId 商品id
     * @return List<Attr>
     */
    List<Attr> queryOutAttrListByGoodsId(String goodsId);

    Attr selectByCode(@Param("attrCode") String attrCode);

    @DisableTenantParam
    Attr selectByCodeNoTenantId(@Param("attrCode") String attrCode);

    /**
     * 根据目录id,商品id,sku三者过滤查询属性
     * @param req QryGoodsAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    List<GoodsCatAttrResp> qryAttrListByFilter(@Param("req") QryGoodsAttrListReq req);

    @DisableTenantParam
    List<Attr> queryAttrArray(@Param("request") QueryAttrListReq request);
}
