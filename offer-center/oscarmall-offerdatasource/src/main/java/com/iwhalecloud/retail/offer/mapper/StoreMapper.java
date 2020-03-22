package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.QryStoreReq;
import com.iwhalecloud.retail.offer.dto.req.QueryStoreReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreByOrgIdResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreDetailResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryStoreResp;
import com.iwhalecloud.retail.offer.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanxiaofei
 * @date  2019/04/28
 */
@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    /**
     * 查询所有店铺
     * @return QueryStoreResp
     */
    List<QueryStoreResp> queryStore();

    /**
     * 更新店铺信誉分
     */
    @DisableTenantParam
    void updateStoreByCreditScore(@Param("creditScore") Long creditScore, @Param("storeId") String storeId);

    /**
     * 分页查询店铺
     * 租户id放在入参，因为存在平台管理员，平台管理员查询全部店铺
     * @param page Page<QueryStoreResp>
     * @param req QueryStoreReq
     * @return Page<QueryStoreResp>
     */
    Page<QueryStoreResp> queryStorePage(Page<QueryStoreResp> page, @Param("req") QueryStoreReq req);

    @DisableTenantParam
    List<QueryStoreResp> qryStoreByCond(@Param("req") QryStoreReq req);


    /**
     * 根据店铺id查详情
     * @param storeId String
     * @return QueryStoreDetailResp
     */
    QueryStoreDetailResp queryStoreDetail(String storeId);


    /**
     * 根据机构id查详情
     * @param orgId String
     * @return QueryStoreDetailResp
     */
    QueryStoreDetailResp queryStoreDetailByOrgId(String orgId);


    /**
     * 根据店铺名称查找店铺
     * @param storeName String
     * @return QueryStoreResp
     */
    QueryStoreResp queryStoreByStoreName(String storeName);

    /**
     * 计算子店铺数量
     * @param storeId String
     * @return int
     */
    int countParentStoreById(String storeId);


    /**
     * 根据机构Id查询关联的店铺列表
     * @param orgId String
     * @return  List<QueryStoreByOrgIdResp>
     */
    List<QueryStoreByOrgIdResp> qryStoreByOrgId(String orgId);

    /**
     * 根据店铺id查询机构id
     * @param storeId String
     * @return String
     */
    @DisableTenantParam
    String queryOrgIdByStoreId(@Param("storeId") String storeId);

    /**
     * 更新店铺信誉分
     * @param rate String
     * @param storeId String
     */
    void updateCreditScoreByRateAndStoreId(@Param("rate") String rate, @Param("storeId") String storeId);
}
