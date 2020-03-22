package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.entity.TenantApplyCatg;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author fanxiaofei
 * @date  2019/05/22
 */
@Mapper
public interface TenantApplyCatgMapper extends BaseMapper<TenantApplyCatg> {
    @DisableTenantParam
    void addTenantApplyCatg(TenantApplyCatg tenantApplyCatg);
}
