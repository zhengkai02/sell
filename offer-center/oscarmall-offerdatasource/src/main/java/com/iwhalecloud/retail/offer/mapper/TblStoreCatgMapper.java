package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.TblStoreCatg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TblStoreCatgMapper extends BaseMapper<TblStoreCatg> {

    List<TblStoreCatgResp> qryStoreCatgOfPage(@Param("req") TblStoreCatgQryReq req);

    List<TblStoreCatg> qryChildNode(String catId);

    List<ProdGoods> qryEffectiveGoodsBycatId(String catId);

    /**
     * 根据商品Id查找店铺目录
     *
     * @param goodsId String
     * @return QueryStoreResp
     */
    TblStoreCatg queryStoreCatByGoodsId(String goodsId);

    /**
     * 根据目录id查询所有商品id
     *
     * @param catId String
     * @return List<String>
     */
    List<String> queryGoodsIdByStoreCatgId(@Param("catId") String catId);

    void deleteStoreCatgById(@Param("catId") String catId, @Param("modifyBy") String modifyBy);

    void updateSyncStateByCatId(String catId, String syncState);

}
