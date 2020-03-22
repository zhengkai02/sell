package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferCatgResp;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsCatReq;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatListResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/25 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface ProdGoodsCatMapper extends BaseMapper<ProdGoodsCat> {

    @DisableTenantParam
    List<ProdGoodsCat> qryProdGoodsCatList(QryProdGoodsCatReq request);

    @DisableTenantParam
    List<ProdGoodsCat> qryOutProdGoodsCatList(QryProdGoodsCatReq request);

    List<ProdGoodsCat> qrySameCatNameList(@Param("catName") String catName, @Param("catType") String catType);

    List<ProdGoodsCat> qryChildProdGoodsCatList(@Param("parentId") String parentId);

    List<OfferCatgResp> qryOfferCatg();

    /**
     * 根据商品id取所有销售类型的目录
     * 
     * @param goodsId 商品id
     * @return List<ProdGoodsCat>
     */
    List<ProdGoodsCat> qryProdGoodsByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 查询所有类型是销售目录的商品目录
     * 
     * @return List<ProdGoodsCat>
     */
    List<ProdGoodsCat> listProdGoodsCatByCatTypeIsS();

    /**
     * 查询店铺的所有销售目录
     * 
     * @param storeId 店铺id
     * @return List<ProdGoodsCat>
     */
    List<ProdGoodsCat> listProdGoodsCatByStoreId(String storeId);

    /**
     * 查询销售目录管理的品牌
     * 
     * @param catId
     * @return
     */
    List<TblGoodsBrandTenantResp> qryGoodBrandByCatgId(@Param("catId") String catId);

    List<QryGoodsCatListResp> qryFormatGoodsCatList(@Param("req") QryProdGoodsCatReq req);

    List<String> qrySaleRuleObj(@Param("storeId") String storeId);

    @DisableTenantParam
    ProdGoodsCat qrySaleCatNonCampaignByGoodsId(@Param("goodsId") String goodsId);

    @DisableTenantParam
    ProdGoodsCat qryMgnCatBySaleCatId(@Param("saleCatId") String saleCatId);

    ArrayList<ProdGoodsCat> qryAllChildMgnCat();

    int selectGoodsCountByCatId(@Param("catId") String catId);

    @DisableTenantParam
    ProdGoodsCat qryMgnCatByCatId(@Param("catId") String catId);

    @DisableTenantParam
    void updateMgnCatByCatId(@Param("req") ProdGoodsCat req);

    void deleteCat(@Param("catId") String catId, @Param("modifyBy") String modifyBy);
}
