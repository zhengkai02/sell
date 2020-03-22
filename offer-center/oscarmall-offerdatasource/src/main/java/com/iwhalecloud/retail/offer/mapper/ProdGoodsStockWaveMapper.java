package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsStockWaveReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsStockWaveResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsStockWave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author fanxiaofei
 * @date 2019/03/04
 */
@Mapper
public interface ProdGoodsStockWaveMapper extends BaseMapper<ProdGoodsStockWave> {

    /**
     * 商品的上架波次,排除状态为X的
     * @param goodsId 商品id
     * @return List<ProdGoodsStockWave>
     */
    List<ProdGoodsStockWaveResp> listProdGoodsStockWaveByProdGoodsId(String goodsId);

    /**
     * 商品的上架波次,排除状态为X,C的
     * @param goodsId 商品id
     * @return List<ProdGoodsStockWave>
     */
    List<ProdGoodsStockWaveResp> qryActiveStockWaveListByProdGoodsId(String goodsId);

    /**
     * 查询是否有相同上架日期的波次
     * @param goodsId 商品id
     * @return ProdGoodsStockWaveResp
     */
    ProdGoodsStockWaveResp qrySameDateProdGoodsStockWave(@Param("goodsId") String goodsId, @Param("req")ProdGoodsStockWaveReq req);

    /**
     * 删除库存上下架,状态修改为X
     * @param stockWaveId id
     */
    void deleteProdGoodsStockWave(@Param("stockWaveId") String stockWaveId, @Param("modifyBy") String modifyBy);

    /**
     * 修改库存上下架
     * @param stockWaveId String
     * @param req ProdGoodsStockWaveReq
     */
    void editProdGoodsStockWave(@Param("stockWaveId") String stockWaveId, @Param("req")ProdGoodsStockWaveReq req);
}
