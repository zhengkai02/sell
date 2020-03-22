package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.QueryUsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.req.UsefulUselessReq;
import com.iwhalecloud.retail.offer.dto.resp.QueryUsefulUselessResp;
import com.iwhalecloud.retail.offer.entity.UsefulUseless;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author fanxiaofei
 * @date  2019/05/07
 */
@Mapper
public interface UsefulUselessMapper extends BaseMapper<UsefulUseless> {


    /**
     * 根据类型和id查询用户点赞
     * @param req QueryUsefulUselessReq
     * @return QueryUsefulUselessResp
     */
    QueryUsefulUselessResp queryByObjTypeAndObjId(@Param("req") QueryUsefulUselessReq req);

    /**
     * 根据类型和id查询用户点赞数量
     * @param req QueryUsefulUselessReq
     * @return int
     */
    @DisableTenantParam
    int countByObjTypeAndObjId(@Param("req") QueryUsefulUselessReq req);


    /**
     * 修改state为X
     * @param req QueryUsefulUselessReq
     */
    void updateStateByObjTypeAndObjId(@Param("req") UsefulUselessReq req);
}
