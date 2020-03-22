package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据机构Id查询关联的店铺列表
 * 
 * @author fanxiaofei
 * @date 2019-06-25
 */
@Data
public class QueryStoreByOrgIdReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构ID")
    private String orgId;

}
