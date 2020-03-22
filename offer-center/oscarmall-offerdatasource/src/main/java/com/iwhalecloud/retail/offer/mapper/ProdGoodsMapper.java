package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.offer.dto.client.req.OfferMobileQryReq;
import com.iwhalecloud.retail.offer.dto.client.req.OfferQryReq;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferMobileQryResp;
import com.iwhalecloud.retail.offer.dto.client.resp.OfferQryResp;
import com.iwhalecloud.retail.offer.dto.req.AddProdBuyCountReq;
import com.iwhalecloud.retail.offer.dto.req.CpspQueryOfferDetailsReq;
import com.iwhalecloud.retail.offer.dto.req.CpspQueryOfferListReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryTargetReq;
import com.iwhalecloud.retail.offer.dto.req.QryProdGoodsListReq;
import com.iwhalecloud.retail.offer.dto.resp.CpspQueryOfferDetailsResp;
import com.iwhalecloud.retail.offer.dto.resp.CpspQueryOfferResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.dto.resp.RightsGoodsResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;


/**
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Mapper
public interface ProdGoodsMapper extends BaseMapper<ProdGoods> {

    /**
     * 商品列表查询（分页）
     * @param page Page<ProdGoodsDTO>
     * @param req ProdGoodsQueryReq
     * @return Page<ProdGoodsDTO>
     */
    Page<ProdGoodsDTO> queryGoodsForPage(Page<ProdGoodsDTO> page, @Param("req") ProdGoodsQueryReq req);

    /**
     * 查询目标商品信息
     * @param req ProdGoodsQueryReq
     * @return List<ProdGoodsDTO>
     */
    Page<QryGoodsCatMemListResp> qryTargetGoodsList(Page<QryGoodsCatMemListResp> page, @Param("request") ProdGoodsQueryTargetReq req);

    int goodsUnpublish(@Param("goodsId") String goodsId, @Param("modifyBy") String modifyBy);

    List<ProdGoodsDTO> queryGoodsList(@Param("req") ProdGoodsQueryReq req);

    @DisableTenantParam
    List<ProdGoodsDTO> queryGoodsListNoTenantId(@Param("req") ProdGoodsQueryReq req);

    @DisableTenantParam
    ArrayList<ProdGoods> queryProdGoodsListByIds(@Param("req") QryProdGoodsListReq req);

    @DisableTenantParam
    ProdGoods qryGoodsBySn(@Param("sn") String sn, @Param("allFlag") String allFlag);

    /**
     * 根据商品id查询商品
     * 异步回调调用没有tenantId
     * @param goodsId String
     * @param allFlag String
     * @return ProdGoods
     */
    @DisableTenantParam
    ProdGoods selectByGoodsId(@Param("goodsId") String goodsId, @Param("allFlag") String allFlag);

    /**
     * CPSP查询商品详情
     * @param req CpspQueryOfferDetailsReq
     * @return CpspQueryOfferDetailsResp
     */
    CpspQueryOfferDetailsResp qryCPSPDetailByGoodsId(@Param("req") CpspQueryOfferDetailsReq req);

    void deleteProdGoods(@Param("goodsId") String goodsId, @Param("modifyBy") String modifyBy);

    void batchDeleteProdGoods(@Param("goodsIdArray") String[] goodsIdArray, @Param("modifyBy") String modifyBy);

    void batchPutOnProdGoods(@Param("goodsIdArray") String[] goodsIdArray, @Param("modifyBy") String modifyBy);

    void batchPullOffProdGoods(@Param("goodsIdArray") String[] goodsIdArray, @Param("modifyBy") String modifyBy);

    /**
     * 根据商品id查询商品名称
     * @param goodsIdList 商品id集合
     * @return List<RightsGoodsResp>
     */
    List<RightsGoodsResp> queryGoodsNameByGoodsId(@Param("goodsIdList") List<String> goodsIdList);

    /***
     * @Author wangzhongbao
     * @Description 查询商品（亚信调用）
     * @Date 12:23 2019/3/20
     * @Param [req]
     * @return OfferQryResp
     **/
    List<OfferQryResp> qryOfferList(@Param("req") OfferQryReq req);

    /***
     * @Author wangzhongbao
     * @Description 查询商品（亚信调用）
     * @Date 12:23 2019/3/20
     * @Param [req]
     * @return OfferQryResp
     **/
    @DisableTenantParam
    List<OfferQryResp> qryOfferListNoTenantId(@Param("req") OfferQryReq req);

    /**
     * @Author wangzhongbao
     * @Description 查询商品不含有分页条件（H5）
     * @Date 13:11 2019/3/21
     * @Param [req]
     * @return OfferMobileQryResp
     **/
    List<OfferMobileQryResp> qryMobileOfferList(@Param("req") OfferMobileQryReq req);

    /**
     * 更新商品销量，异步支付没有登录，更新可以直接根据商品id
     * @param request
     * @return
     */
    @DisableTenantParam
    int addProdBuyCount(@Param("request") List<AddProdBuyCountReq> request);

    int reduceProdBuyCount(@Param("request") List<AddProdBuyCountReq> request);

    int updateProdGoodsWithOutBuyCount(ProdGoods prodGoods);


    /**
     * 更新商品新增库存
     * @param goodsId
     * @param qty
     */
    void incrGoodsStockById(@Param("goodsId") String goodsId, @Param("qty") Long qty);

    /**
     * 查询状态为C且下架时间+5分钟（job间隙5分钟）所有的商品
     * @return List<ProdGoods>
     */
    List<ProdGoods> qryList4SynGoodsQty();

    /**
     * 查询状态为C且下架时间到期的所有商品id
     * @return List<ProdGoods>
     */
    @DisableTenantParam
    List<String> qryListByStateCAndMarketingEndTime();

    /**
     * 修改状态为C且下架时间到期的所有商品
     */
    @DisableTenantParam
    void updateByStateCAndMarketingEndTime(@Param("prodGoodsIds") List<String> prodGoodsList);

    /**
     * 查询状态为C和F的所有商品
     * @return List<ProdGoods>
     */
    @DisableTenantParam
    List<ProdGoods> qryListByStateCAndStateF();

    /**
     * @Author wangzhongbao
     * @Description 查询商品，含有分页信息
     * @Date 10:36 2019/5/21
     * @Param [req, couponGoodsIds, offerTypes, salesConditionIds]
     * @return java.util.List<com.iwhalecloud.retail.offer.dto.client.resp.OfferMobileQryResp>
     **/
    List<OfferMobileQryResp> qryMobileOfferPageList(@Param("req") OfferMobileQryReq req, @Param("couponGoodsIds") List<String> couponGoodsIds,
                                                    @Param("offerTypes") List<String> offerTypes, @Param("catGoods")List<String> catGoods);

    List<ProdGoodsDTO> querySaleableGoodsList();

    /**
     * MQ没有tenantId,审批商品
     * @param goodsId String
     * @param state String
     */
    @DisableTenantParam
    void updateStateByGoodsId(@Param("goodsId") String goodsId, @Param("state") String state);

    /**
     * CPSP商品列表查询（分页）
     * @param page Page<CpspQueryOfferResp>
     * @param req CpspQueryOfferListReq
     * @return Page<CpspQueryOfferResp>
     */
    @DisableTenantParam
    Page<CpspQueryOfferResp> qryCPSPOfferList(Page<CpspQueryOfferResp> page, @Param("req") CpspQueryOfferListReq req);

    /**
     * job更新商品评价
     * @param goodsId String
     * @param evaluationCount Integer
     * @param evaluationRate Integer
     */
    @DisableTenantParam
    void updateEvaluationByGoodsId(@Param("goodsId") String goodsId, @Param("evaluationCount") Integer evaluationCount,
                                   @Param("evaluationRate") Integer evaluationRate);

    @DisableTenantParam
    void updateProGoodsByES(@Param("goodsId") String goodsId);

    /**
     * 根据店铺id修改状态
     * @param storeId String
     */
    void updateProGoodsStateByStoreId(@Param("storeId") String storeId, @Param("modifyBy") String modifyBy,
                                      @Param("oldState") String oldState, @Param("state") String state);

    /**
     * 店铺下所有上架商品id
     * @param storeId String
     * @return String
     */
    List<String> queryProGoodsIdByStoreId(@Param("storeId") String storeId);

    @DisableTenantParam
    void updateSyncStateByGoodsId(@Param("goodsId") String goodsId, @Param("syncState") String syncState);
}
