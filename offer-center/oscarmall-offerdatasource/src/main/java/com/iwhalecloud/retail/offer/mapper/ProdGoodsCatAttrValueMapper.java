package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatAttrListReq;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author hu.minghang
 * @date 2019/05/28
 */
@Mapper
public interface ProdGoodsCatAttrValueMapper extends BaseMapper<ProdGoodsCatAttrValue> {

    @DisableTenantParam
    Page<GoodsCatAttrResp> qryProdGoodsCatAttrList(Page<GoodsCatAttrResp> page, @Param("request") QryGoodsCatAttrListReq req);

    Page<GoodsCatAttrResp> qryOutAttrListByCatId(Page<GoodsCatAttrResp> page, @Param("request") QryGoodsCatAttrListReq req);

    /**
     * 根据目录id取所有目录属性按照sku和非sku属性封装
     * @param catId String
     * @return List<GoodsCatAttrResp>
     */
    @DisableTenantParam
    List<GoodsCatAttrResp> qrySkuGoodsCatAttrByCatId(@Param("catId") String catId);

    @DisableTenantParam
    List<ProdGoodsCatAttrValue> qryCatAttrById(@Param("catId") String catId);

    void deleteCatAttrListByCatId(@Param("catId") String catId, @Param("modifyBy") String modifyBy);

    void insertCatAttr(ProdGoodsCatAttrValue newCatAttr);

}
