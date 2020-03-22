package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsCatMemListReq;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsCatMemResp;
import com.iwhalecloud.retail.offer.dto.resp.QryGoodsCatMemListResp;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatMem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/28 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface ProdGoodsCatMemMapper extends BaseMapper<ProdGoodsCatMem> {

    List<ProdGoodsCatMem> selectProdGoodsCatMemByCat(@Param("catId") String catId);

    List<QryGoodsCatMemListResp> qryProdGoodsCatMemDetailByCatId(@Param("catId") String catId);

    Page<QryGoodsCatMemListResp> qryGoodsCatMemList(Page<QryGoodsCatMemListResp> page, @Param("request") QryGoodsCatMemListReq req);

    /**
     * 根据商品id查询所对应的所有目录
     * @author fanxiaofei
     * @date 2019/02/02
     * @param goodsId 商品id
     * @return List<ProdGoodsCatMapper>
     */
    List<ProdGoodsCatMemResp> listProdGoodsCatByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 
     * Description: 批量删除上面目录成员<br> 
     *  
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param offerIds
     * @return <br>
     */
    int batchDeleteGoodsMem(@Param("goodsIds") List<String> goodsIds, @Param("modifyBy") String modifyBy);

    /**
     * 
     * Description: 查询已发布商品的数量<br> 
     *  
     * @author wang.zhongbao<br>
     * @taskId <br>
     * @param offerIds
     * @return <br>
     */
    Long qryPublishMem(@Param("goodsIds") List<String> goodsIds);

    /**
     * 目录id下绑定商品的数量
     * @param catId String
     * @return Integer
     */
    Integer countProGoodsByCatId(@Param("catId") String catId);

    /**
     * 目录id下的商品id
     * @param catId String
     * @return String
     */
    List<String> qryGoodsIdByCatId(@Param("catId") String catId);

    void deleteCatMemById(@Param("catId") String catId, @Param("modifyBy") String modifyBy);
}
