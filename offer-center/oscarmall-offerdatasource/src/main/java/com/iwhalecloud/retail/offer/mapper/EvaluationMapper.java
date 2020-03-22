package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.BatchAuditEvaluationReq;
import com.iwhalecloud.retail.offer.dto.req.ContentEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationModeReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.QueryEvaluationByUserIdAndObjReq;
import com.iwhalecloud.retail.offer.dto.resp.ContentEvaluationQryResp;
import com.iwhalecloud.retail.offer.dto.resp.EvaluationResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsEvaluationQryResp;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.entity.Evaluation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0<br>
 */
@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {


    /**
     * 计算对象类型和对象id查询的总数
     * @param objType 对象类型 A 商品, B 内容
     * @param objId 对象ID
     * @return Long
     */
    @DisableTenantParam
    Integer countListByObjTypeAndObjId(@Param("objType") String objType, @Param("objId") String objId);


    /**
     * 计算本次时间对象类型和对象id查询的总数
     * @param objType 对象类型 A 商品, B 内容
     * @param objId 对象ID
     * @param preTime 上一段时间
     * @param time 本次时间
     * @return Long
     */
    @DisableTenantParam
    Integer countListByObjTypeAndObjIdAndTime(@Param("objType") String objType, @Param("objId") String objId,
                                              @Param("preTime") Date preTime, @Param("time") Date time);

    /**
     * 计算对象类型和对象id且rate为好评的总数
     * @param objType 对象类型 A 商品, B 内容
     * @param objId 对象ID
     * @return Long
     */
    @DisableTenantParam
    Integer countRateIsFiveListByObjTypeAndObjId(@Param("objType") String objType, @Param("objId") String objId);

    /**
     * 计算总评分
     * @param objType 对象类型 A 商品, B 内容
     * @param objId 对象ID
     * @return Long
     */
    @DisableTenantParam
    Integer sumRateByObjTypeAndObjId(@Param("objType") String objType, @Param("objId") String objId);

    /**
     * 计算本次时间总评分
     * @param objType 对象类型 A 商品, B 内容
     * @param objId 对象ID
     * @param preTime 上一段时间
     * @param time 本次时间
     * @return Long
     */
    @DisableTenantParam
    Integer sumRateByObjTypeAndObjIdAndTime(@Param("objType") String objType, @Param("objId") String objId,
                                            @Param("preTime") Date preTime, @Param("time") Date time);
    /**
     * 更新赞 数量
     * @param usefulCount Integer
     * @param evaluationId String
     */
    void updateUsefulCountById(@Param("usefulCount") Integer usefulCount, @Param("evaluationId") String evaluationId);

    /**
     * 更新踩 数量
     * @param uselessCount Integer
     * @param evaluationId String
     */
    void updateUselessCountById(@Param("uselessCount") Integer uselessCount, @Param("evaluationId") String evaluationId);

    @DisableTenantParam
    Page<EvaluationResp> qryEvaluation(Page<EvaluationResp> page, @Param("req") EvaluationQryReq req) throws BaseException;

    /**
     * 查询商品评论列表
     * @param page
     * @param req
     */
    Page<ProdGoodsEvaluationQryResp> qryProdGoodsEvaluation(Page<ProdGoodsEvaluationQryResp> page, @Param("req") ProdGoodsEvaluationQryReq req);

    /**
     * 查询文章评论列表
     * @param page
     * @param req
     */
    Page<ContentEvaluationQryResp> qryContentEvaluation(Page<ContentEvaluationQryResp> page, @Param("req") ContentEvaluationQryReq req);

    /**
     * @Author wangzhongbao
     * @Description 批量删除评论
     * @Date 14:08 2019/5/10
     * @Param [ids]
     * @return int
     **/
    int batchDelEvaluation(@Param("ids") List<String> ids, @Param("modifyBy") String modifyBy) throws BaseException;

    /**
     * @Author wangzhongbao
     * @Description 批量隐藏
     * @Date 14:08 2019/5/10
     * @Param [ids]
     * @return int
     **/
    int batchHide(@Param("ids") List<String> ids, @Param("modifyBy") String modifyBy) throws BaseException;

    /**
     * @Author wangzhongbao
     * @Description 批量展示
     * @Date 14:08 2019/5/10
     * @Param [ids]
     * @return int
     **/
    int batchDisplay(@Param("ids") List<String> ids, @Param("modifyBy") String modifyBy) throws BaseException;

    /**
     * @Author wangzhongbao
     * @Description 修改评论
     * @Date 14:46 2019/5/10
     * @Param [req]
     * @return int
     **/
    int modEvaluation(@Param("req") EvaluationModeReq req) throws BaseException;

    /**
     * 批量审核
     * @param req BatchAuditEvaluationReq
     */
    void batchAudit(@Param("req") BatchAuditEvaluationReq req);

    /**
     * 根据用户id和obj查询评价
     * @param req QueryEvaluationByUserIdAndObjReq
     * @return EvaluationResp
     */
    EvaluationResp qryEvaluationByObjAndUserId(@Param("req") QueryEvaluationByUserIdAndObjReq req);

    /**
     * 根据店铺ID计算所有评分
     * @param storeId 店铺ID
     * @return Integer
     */
    @DisableTenantParam
    Long sumRateByStoreId(@Param("storeId") String storeId);

    /**
     * 一个月用户评价一个店铺次数
     * @param userId 用户ID
     * @param storeId 店铺ID
     * @param preTime 月初
     * @param time 月末
     * @return Long
     */
    Long countEvaluationByUserIdAndStoreId(@Param("userId") String userId, @Param("storeId") String storeId,
                                           @Param("preTime") Date preTime, @Param("time") Date time);
}
