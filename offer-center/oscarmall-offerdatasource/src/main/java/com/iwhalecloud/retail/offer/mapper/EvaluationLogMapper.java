package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.PageEvaluationLogReq;
import com.iwhalecloud.retail.offer.dto.resp.PageEvaluationLogResp;
import com.iwhalecloud.retail.offer.entity.EvaluationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author fanxiaofei
 * @date  2019/05/07
 */
@Mapper
public interface EvaluationLogMapper extends BaseMapper<EvaluationLog> {


    /**
     * 根据对象ID查询商品最新一条计算日志
     * @param objId String
     * @return EvaluationLog
     */
    @DisableTenantParam
    EvaluationLog queryLastByObjId(String objId);


    /**
     * 评价计算日志分页
     * @param page Page<PageEvaluationLogResp>
     * @param req PageEvaluationLogReq
     * @return Page<PageEvaluationLogResp>
     */
    Page<PageEvaluationLogResp> pageEvaluationLog(Page<PageEvaluationLogResp> page, @Param("req") PageEvaluationLogReq req);

    /**
     * 新增评价日志
     * job触发没有租户id
     * @param evaluationLog EvaluationLog
     */
    @DisableTenantParam
    void insertByJob(@Param("req") EvaluationLog evaluationLog);
}
