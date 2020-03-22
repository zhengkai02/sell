package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.AddManagementCatReq;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author fanxiaofei
 * @date  2019/05/22
 */
@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 租户所有可使用的目录标识
     * @param tenantId 租户id
     * @return List<QryGoodsCatListResp>
     */
    @DisableTenantParam
    List<String> listManagementCatIdByTenantId(String tenantId);

    /**
     * 租户所有可使用的商品目录
     * @param tenantId 租户id
     * @return List<QryGoodsCatListResp>
     */
    @DisableTenantParam
    List<ProdGoodsCat> listManagementCatByTenantId(String tenantId);

    /**
     * 删除租户所有可使用目录,逻辑删除,状态改为X
     * @param req AddManagementCatReq
     */
    @DisableTenantParam
    void deleteManagementCatByTenantId(AddManagementCatReq req);
}
