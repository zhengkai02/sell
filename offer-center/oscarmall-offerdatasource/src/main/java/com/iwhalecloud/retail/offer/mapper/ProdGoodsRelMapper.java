package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsRelReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsRelResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsRel;

/**
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Mapper
public interface ProdGoodsRelMapper extends BaseMapper<ProdGoodsRel> {

    /**
     * 商品下的所有关系
     * 
     * @param goodsId String
     * @return List<ProdGoodsRel>
     */
    List<ProdGoodsRelResp> listProdGoodsRelByProdGoodsId(@Param("goodsId") String goodsId);

    /**
     * Description: 批量新增商品关系 <br>
     * 
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param goodsRelList <br>
     * @return <br>
     */
    int createProdGoodsRel(@Param("goodsRelList") List<ProdGoodsRel> goodsRelList);

    /**
     * Description: 查询已存在的商品关系<br>
     * 
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param agoodsId <br>
     * @param relType <br>
     * @param zgoodsIds <br>
     * @return <br>
     */
    List<ProdGoodsRel> qryExistRel(@Param("id") String agoodsId, @Param("relType") String relType,
        @Param("goodsList") List<String> zgoodsIds);

    /**
     * Description: 批量删除商品关系<br>
     * 
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param existGoodsRels <br>
     * @return <br>
     */
    int delProdGoodsRel(@Param("goodsRelList") List<ProdGoodsRel> existGoodsRels, @Param("modifyBy") String modifyBy);

    /**
     * 清除所有aGoodsId商品的关系
     * 
     * @param aGoodsId String
     */
    void delProdGoodsRelByAGoodsId(@Param("aGoodsId") String aGoodsId, @Param("modifyBy") String modifyBy);

    /**
     * 根据类型和AgoodsId删除商品关系
     * 
     * @param aGoodsId
     * @param relType
     * @param modifyBy
     */
    void delProdGoodsRelByAGoodsIdAndType(@Param("aGoodsId") String aGoodsId, @Param("relType") String relType,
        @Param("modifyBy") String modifyBy);

    /**
     * Description: 查询互斥商品<br>
     * 
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param req
     * @return <br>
     */
    List<ProdGoods> qryExcludeProdGoods(@Param("goodsRel") ProdGoodsRelReq req);
}
