package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgMemQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgMemResp;
import com.iwhalecloud.retail.offer.entity.TblStoreCatgMem;
import org.apache.ibatis.annotations.Param;

public interface TblStoreCatgMemMapper extends BaseMapper<TblStoreCatgMem> {


    /**
     * @Author wangzhongbao
     * @Description 查询店铺商品目录
     * @Date 15:56 2019/4/29
     * @Param [req]
     * @return java.util.List<com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgMemResp>
     */
    Page<TblStoreCatgMemResp> qryStoreCatgMem(Page<TblStoreCatgMemResp> page, @Param("req") TblStoreCatgMemQryReq req);

    void deleteStoreCatgMemByGoodsId(@Param("goodsId") String goodsId, @Param("modifyBy") String modifyBy);

    void deleteStoreCatgMemById(@Param("catMemId") String catMemId, @Param("modifyBy") String modifyBy);
}
