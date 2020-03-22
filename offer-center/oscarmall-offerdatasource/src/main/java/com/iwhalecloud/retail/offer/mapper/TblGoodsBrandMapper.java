package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.entity.TblGoodsBrand;

/**
 * @Author wangzhongbao
 * @Description //TODO 
 * @Date 10:09 2019/5/13
 * @Param 
 * @return 
 **/
@Mapper
public interface TblGoodsBrandMapper extends BaseMapper<TblGoodsBrand> {


    /**
     * @Author wangzhongbao
     * @Description 查询所有商品品牌
     * @Date 10:24 2019/5/15
     * @Param []
     * @return java.util.List<com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp>
     **/
    List<TblGoodsBrandQryResp> qryAllGoodsBrandList() throws BaseException;

    /**
     * 查询租户下的品牌
     * @return
     * @throws BaseException
     */
    List<TblGoodsBrandTenantResp> qryGoodsBrandByTenantId() throws BaseException;

    /**
     * @Author wangzhongbao
     * @Description 删除商品品牌
     * @Date 10:24 2019/5/15
     * @Param
     * @return
     **/
    int deleteGoodsBrand(@Param("brandIds") List<String> brandIds, @Param("modifyBy") String modifyBy) throws BaseException;

    /**
     * @Author wangzhongbao
     * @Description 查询商品品牌（分页）
     * @Date 10:24 2019/5/15
     * @Param [page, req]
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp>
     **/
    Page<TblGoodsBrandQryResp> qryGoodsBrandList(Page<TblGoodsBrandQryResp> page, @Param("req") TblGoodsBrandQryReq req);

    int goodsBrandStateNum(@Param("brandIds") List<String> brandIds) throws BaseException;

    List<TblGoodsBrandQryResp> qryGoodsBrandInCatg(String catgId) throws BaseException;
}
