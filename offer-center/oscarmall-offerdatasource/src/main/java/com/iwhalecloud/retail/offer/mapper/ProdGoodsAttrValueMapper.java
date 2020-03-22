package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.CpspQueryOfferDetailsReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsAttrValueReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrValueResp;
import com.iwhalecloud.retail.offer.dto.resp.CpspQueryOfferDetailsAttrListResp;
import com.iwhalecloud.retail.offer.dto.resp.SkuAttrValueResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Mapper
public interface ProdGoodsAttrValueMapper extends BaseMapper<ProdGoodsAttrValue> {

    /**
     * 商品下的商品属性值
     * @param goodsId String
     * @return List<AttrValueResp>
     */
    @DisableTenantParam
    List<AttrValueResp> listAttrValueRespByProdGoodsId(String goodsId);


    /**
     * 商品下的商品属性值
     * @param req CpspQueryOfferDetailsReq
     * @return List<AttrValueResp>
     */
    List<CpspQueryOfferDetailsAttrListResp> listCPSPAttrByProdGoodsId(@Param("req") CpspQueryOfferDetailsReq req);

    /**
     * 删除商品属性值
     * @param goodsId 商品id
     * @param attrId 属性id
     */
    void deleteProdGoodsAttrValue(@Param("goodsId") String goodsId, @Param("attrId") String attrId, @Param("modifyBy") String modifyBy);

    /**
     * 根据GoodsId，删除属性
     * @param goodsId
     * @param modifyBy
     */
    void deleteProdGoodsAttrValueByGoodsId(@Param("goodsId") String goodsId, @Param("modifyBy") String modifyBy);

    /**
     * 修改商品属性值
     * @param req ProdGoodsAttrValueReq
     */
    void editProdGoodsAttrValue(ProdGoodsAttrValueReq req);

    /**
     * 商品下的SPU属性值,后台管理
     * @param goodsId String
     * @return List<SkuAttrValueResp>
     */
    List<SkuAttrValueResp> listSkuAttrValueByProdGoodsId(String goodsId);

    /**
     * 商品下的SPU属性值,商城端
     * @param goodsId String
     * @return List<SkuAttrValueResp>
     */
    @DisableTenantParam
    List<SkuAttrValueResp> listSkuAttrValueByProdGoodsIdForMobile(String goodsId);
}
